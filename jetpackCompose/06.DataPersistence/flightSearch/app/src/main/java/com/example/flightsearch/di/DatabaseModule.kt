package com.example.flightsearch.di

import android.content.Context
import androidx.room.Room
import com.example.flightsearch.data.database.AirportDao
import com.example.flightsearch.data.database.BookmarkDao
import com.example.flightsearch.data.database.FlightDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context):FlightDatabase{
        return Room.databaseBuilder(
            context, FlightDatabase::class.java,"flight_database"
        ).createFromAsset("database/flight_search.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideAirportDao(database: FlightDatabase): AirportDao{
        return database.airportDao()
    }

    @Provides
    fun provideBookmarkDao(database: FlightDatabase):BookmarkDao{
        return database.bookmarkDao()
    }

}