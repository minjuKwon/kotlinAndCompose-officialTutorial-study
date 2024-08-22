package com.example.flightsearch.viewmodel.airport

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.data.model.Airport
import com.example.flightsearch.data.repository.FlightAirportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirportViewModel @Inject constructor(
    private val repository: FlightAirportRepository
) : ViewModel() {

    private val _airportUiState = MutableStateFlow<AirportUiState>(AirportUiState.EmptySearch())
    val airportUiState: StateFlow<AirportUiState> = _airportUiState

    var searchingText by mutableStateOf("")
        private set

    fun updateAirportUiState(newState: AirportUiState) {
        _airportUiState.value = newState
    }

    fun updateText(text:String){
        searchingText=text
    }

    fun getAirportList(word:String){
        viewModelScope.launch {
            _airportUiState.value= AirportUiState.Loading
            _airportUiState.value=try{
                if(word.isEmpty()) AirportUiState.EmptySearch()
                else{
                    val item=repository.getAirportStream(word).first().toItem()
                    val itemList = repository.getAirportsListStream(word).first()
                    AirportUiState.SearchResult(
                        itemList= itemList,
                        item=item
                    )
                }
            }catch(e:Exception){AirportUiState.Error}
        }
    }

    fun searchByKeyword(){
        viewModelScope.launch {
            _airportUiState.value=try{
                if(searchingText.isEmpty()){
                    AirportUiState.EmptySearch()
                }else{
                    val searchList = repository.searchByKeywordStream(searchingText).first()
                    if(searchList.isEmpty()){
                        AirportUiState.EmptySearch(INVALID_QUERY)
                    }else{
                        repository.saveSearchWordPreference(searchingText)
                        AirportUiState.Searching(searchList=searchList)
                    }
                }
            }catch(e:Exception){AirportUiState.Error}
        }
    }

}

data class Item(
    val name:String="",
    val iataCode:String=""
)

fun Airport.toItem(): Item = Item(
    name=name,
    iataCode=iataCode
)