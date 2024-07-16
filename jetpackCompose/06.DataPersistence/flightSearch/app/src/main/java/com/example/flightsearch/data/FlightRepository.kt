package com.example.flightsearch.data

import kotlinx.coroutines.flow.Flow

interface FlightRepository {

    fun getAirportStream(keyword: String): Flow<Airport>

    fun getAirportsListStream(keyword:String):Flow<List<Airport>>

    fun searchByKeywordStream(keyword: String):Flow<List<Airport>>

    fun getAllBookmarkStream():Flow<List<Bookmark>>

    suspend fun insertBookmarkData(bookmark: Bookmark)

    suspend fun deleteBookmarkData(bookmark: Bookmark)

}