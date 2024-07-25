package com.example.flightsearch.data.repository

import com.example.flightsearch.data.model.Bookmark
import kotlinx.coroutines.flow.Flow

interface FlightBookmarkRepository {
    fun getAllBookmarkStream(): Flow<List<Bookmark>>?

    suspend fun insertBookmarkData(bookmark: Bookmark)

    fun deleteBookmarkData(bookmark: Bookmark)

    suspend fun getBookmarkDataCount():Int
}