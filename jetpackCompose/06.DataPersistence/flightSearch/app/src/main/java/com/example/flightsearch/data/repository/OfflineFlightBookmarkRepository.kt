package com.example.flightsearch.data.repository

import com.example.flightsearch.data.database.BookmarkDao
import com.example.flightsearch.data.model.Bookmark
import kotlinx.coroutines.flow.Flow

class OfflineFlightBookmarkRepository(
    private val bookmarkDao: BookmarkDao
): FlightBookmarkRepository {

    override fun getAllBookmarkStream(): Flow<List<Bookmark>>
            =bookmarkDao.getAllBookmark()

    override suspend fun insertBookmarkData(bookmark: Bookmark)
            =bookmarkDao.insertBookmark(bookmark)

    override suspend fun deleteBookmarkData(bookmark: Bookmark)
            =bookmarkDao.deleteBookmark(bookmark)

}