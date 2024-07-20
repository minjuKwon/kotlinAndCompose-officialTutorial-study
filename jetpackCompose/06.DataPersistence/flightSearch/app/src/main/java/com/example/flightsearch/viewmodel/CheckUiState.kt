package com.example.flightsearch.viewmodel

import com.example.flightsearch.viewmodel.airport.AirportUiState

fun checkAirportScreenState(
    airportUiState: AirportUiState,
    searchingUiState:(AirportUiState.Searching)->AirportUiState,
    resultUiState:(AirportUiState.SearchResult)->AirportUiState,
):AirportUiState{
    return checkAirportUiState(
        airportUiState=airportUiState,
        searchingUiState = searchingUiState,
        resultUiState = resultUiState,
        emptyUiState = {airportUiState},
        loadingUiState = {airportUiState},
        errorUiState = {airportUiState}
    )
}


fun <T>checkAirportUiState(
    airportUiState: AirportUiState,
    searchingUiState:(AirportUiState.Searching)->T,
    resultUiState:(AirportUiState.SearchResult)->T,
    emptyUiState:(AirportUiState.EmptySearch)->T,
    loadingUiState:(AirportUiState.Loading)->T,
    errorUiState:(AirportUiState.Error)->T
):T{
    return when(airportUiState){
        is AirportUiState.Searching -> searchingUiState(airportUiState)
        is AirportUiState.SearchResult ->resultUiState(airportUiState)
        is AirportUiState.EmptySearch -> emptyUiState(airportUiState)
        is AirportUiState.Loading->loadingUiState(airportUiState)
        is AirportUiState.Error -> errorUiState(airportUiState)
    }
}