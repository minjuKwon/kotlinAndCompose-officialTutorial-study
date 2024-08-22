package com.example.flightsearch.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flightsearch.data.model.Airport
import com.example.flightsearch.data.model.Bookmark

@Database(entities = [Airport::class, Bookmark::class], version=1)
abstract class FlightDatabase :RoomDatabase() {
    abstract fun airportDao(): AirportDao
    abstract fun bookmarkDao(): BookmarkDao
}