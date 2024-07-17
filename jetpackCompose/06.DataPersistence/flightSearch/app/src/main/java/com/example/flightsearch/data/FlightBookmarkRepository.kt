package com.example.flightsearch.data

import kotlinx.coroutines.flow.Flow

interface FlightBookmarkRepository {
    fun getAllBookmarkStream(): Flow<List<Bookmark>>

    suspend fun insertBookmarkData(bookmark: Bookmark)

    suspend fun deleteBookmarkData(bookmark: Bookmark)
}