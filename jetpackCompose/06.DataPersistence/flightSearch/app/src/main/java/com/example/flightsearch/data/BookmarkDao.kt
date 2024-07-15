package com.example.flightsearch.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {

    @Insert
    suspend fun insertBookmark(bookmark: Bookmark)

    @Query("SELECT departure_code, destination_code FROM favorite")
    fun getAllBookmark(): Flow<List<Bookmark>>

}