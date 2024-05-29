package com.example.mycity.ui

import androidx.lifecycle.ViewModel
import com.example.mycity.data.Spot
import com.example.mycity.data.SpotDataProvider
import com.example.mycity.data.SpotType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CityViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CityUiState())
    val uiState: StateFlow<CityUiState> = _uiState

    init {
        initializeUiState()
    }

    private fun initializeUiState(){
        val spots : Map<SpotType, List<Spot>>
        = SpotDataProvider.allSpots.groupBy {it.spotType}

        val bookmarkList:MutableList<Spot>
        = SpotDataProvider.allSpots.filter { it.isBookmark } as MutableList<Spot>

        _uiState.value=
            CityUiState(
                spots=spots,
                bookmarkList= bookmarkList,
                currentSelectSpot= spots[SpotType.Food]?.get(0)
                    ?:SpotDataProvider.defaultSpot
            )
    }

    fun updateDetailScreenStates(spot:Spot){
        _uiState.update{
            it.copy(
                currentSelectSpot=spot,
                isShowingHomePage=false
            )
        }
    }

    fun resetHomeScreenStates(){
        _uiState.update {
            it.copy(
                currentSelectSpot=
                it.spots[it.currentSpotType]?.get(0) ?:SpotDataProvider.defaultSpot,
                isShowingHomePage=true
            )
        }
    }

    fun updateCurrentSpotType(spotType: SpotType){
        _uiState.update {
            it.copy(
                currentSpotType = spotType
            )
        }
    }

    fun resetBookmarkScreen(){
        _uiState.update {
            it.copy(
                currentSelectSpot = it.bookmarkList[0],
                isShowingHomePage = true
            )
        }
    }

    fun updateBookmarkList(spot:Spot){
        SpotDataProvider.updateIsBookmark(spot.id)

        _uiState.update {
            var tempList:MutableList<Spot> = it.bookmarkList
            if(spot.isBookmark){
                tempList= (tempList + spot) as MutableList<Spot>
            }else{
                tempList= (tempList - spot) as MutableList<Spot>
                if(it.currentSpotType==SpotType.Bookmark&&it.bookmarkList.size==0){
                }
            }
            it.copy(
                bookmarkList = tempList
            )
        }
    }

}