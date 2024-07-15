package com.example.flightsearch.data

import kotlinx.coroutines.flow.Flow

class OfflineFlightRepository(
    private val airportDao: AirportDao,
    private val bookmarkDao: BookmarkDao
) :FlightRepository {

    override fun getAirportStream(keyword: String): Flow<Airport>
    =airportDao.getAirport(keyword)

    override fun getAirportsListStream(keyword: String): Flow<List<Airport>>
    = airportDao.getAirportsList(keyword)

    override fun searchByKeywordStream(keyword: String): Flow<List<Airport>>
    = airportDao.searchByKeyword(keyword)

    override fun getAllBookmarkStream(): Flow<List<Bookmark>>
    =bookmarkDao.getAllBookmark()

    override suspend fun insertBookmarkData(bookmark: Bookmark)
    =bookmarkDao.insertBookmark(bookmark)

}