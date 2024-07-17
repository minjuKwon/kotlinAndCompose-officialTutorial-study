package com.example.flightsearch.data

import kotlinx.coroutines.flow.Flow

interface FlightAirportRepository {
    fun getAirportStream(keyword: String): Flow<Airport>

    fun getAirportsListStream(keyword:String):Flow<List<Airport>>

    fun searchByKeywordStream(keyword: String):Flow<List<Airport>>
}