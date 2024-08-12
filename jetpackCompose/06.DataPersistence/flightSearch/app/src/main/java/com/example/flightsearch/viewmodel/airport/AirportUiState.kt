package com.example.flightsearch.viewmodel.airport

import com.example.flightsearch.data.model.Airport

sealed class AirportUiState{
    data class Searching(
        val searchList:List<Airport> = emptyList()
    ): AirportUiState()
    data class SearchResult(
        val itemList:List<Airport> = emptyList(),
        val item: Item = Item()
    ): AirportUiState()
    data class EmptySearch(
        val mode:Int= EMPTY_LIST
    ): AirportUiState()
    object Error:AirportUiState()
    object Loading:AirportUiState()
}

const val EMPTY_LIST=1
const val INVALID_QUERY=2

