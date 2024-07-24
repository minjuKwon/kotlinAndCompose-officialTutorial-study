package com.example.flightsearch.viewmodel.airport

import com.example.flightsearch.data.model.Airport

sealed class AirportUiState{
    data class Searching(
        val searchList:List<Airport> = emptyList(),
        val searchText:String=""
    ): AirportUiState()
    data class SearchResult(
        val itemList:List<Airport> = emptyList(),
        val item: Item = Item(),
        val searchText:String=""
    ): AirportUiState()
    object EmptySearch: AirportUiState()
    object Error:AirportUiState()
    object Loading:AirportUiState()
}