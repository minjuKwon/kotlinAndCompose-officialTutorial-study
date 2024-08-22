package com.example.flightsearch.data.repository

import com.example.flightsearch.data.database.BookmarkDao
import com.example.flightsearch.data.model.Bookmark
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineFlightBookmarkRepository @Inject constructor(
    private val bookmarkDao: BookmarkDao
): FlightBookmarkRepository {

    override fun getAllBookmarkStream(): Flow<List<Bookmark>>
            =bookmarkDao.getAllBookmark()

    override suspend fun insertBookmarkData(bookmark: Bookmark)
            =bookmarkDao.insertBookmark(bookmark)

    override fun deleteBookmarkData(bookmark: Bookmark)
            =bookmarkDao.deleteBookmark(bookmark)

}