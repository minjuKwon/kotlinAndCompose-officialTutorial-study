package com.example.mycity.ui

import com.example.mycity.data.Spot
import com.example.mycity.data.SpotDataProvider
import com.example.mycity.data.SpotType

data class CityUiState(
    val spots:Map<SpotType, List<Spot>> = emptyMap(),
    val bookmarkList:MutableList<Spot> = mutableListOf(),
    val currentSpotType:SpotType = SpotType.Food,
    val currentSelectSpot:Spot=SpotDataProvider.defaultSpot,
    val isShowingHomePage:Boolean=true
){
    val currentSpotTypeList : List<Spot> by lazy{ spots[currentSpotType]!! }
}
