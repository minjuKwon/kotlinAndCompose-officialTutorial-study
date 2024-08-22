package com.example.flightsearch.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.flightsearch.data.database.AirportDao
import com.example.flightsearch.data.database.BookmarkDao
import com.example.flightsearch.data.repository.FlightAirportRepository
import com.example.flightsearch.data.repository.FlightBookmarkRepository
import com.example.flightsearch.data.repository.OfflineFlightAirportRepository
import com.example.flightsearch.data.repository.OfflineFlightBookmarkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideFlightAirportRepository(
        airportDao: AirportDao,
        dataStore: DataStore<Preferences>
    ): FlightAirportRepository {
        return OfflineFlightAirportRepository(airportDao,dataStore)
    }

    @Provides
    fun provideFlightBookmarkRepository(
        bookmarkDao: BookmarkDao
    ): FlightBookmarkRepository {
        return OfflineFlightBookmarkRepository(bookmarkDao)
    }
}