package com.example.flightsearch.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flightsearch.data.model.Airport
import com.example.flightsearch.data.model.Bookmark

@Database(entities = [Airport::class, Bookmark::class], version=1)
abstract class FlightDatabase :RoomDatabase() {

    abstract fun airportDao(): AirportDao
    abstract fun bookmarkDao(): BookmarkDao

    companion object{
        @Volatile
        private var Instance: FlightDatabase?=null

        fun getDatabase(context: Context): FlightDatabase {
            return Instance ?:synchronized(this){
                Room.databaseBuilder(
                    context, FlightDatabase::class.java,"flight_database"
                ).createFromAsset("database/flight_search.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance =it }
            }
        }
    }

}