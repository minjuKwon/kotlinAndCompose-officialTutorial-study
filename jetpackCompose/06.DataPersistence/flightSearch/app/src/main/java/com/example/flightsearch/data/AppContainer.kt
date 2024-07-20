package com.example.flightsearch.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.flightsearch.data.database.FlightDatabase
import com.example.flightsearch.data.repository.FlightAirportRepository
import com.example.flightsearch.data.repository.FlightBookmarkRepository
import com.example.flightsearch.data.repository.OfflineFlightAirportRepository
import com.example.flightsearch.data.repository.OfflineFlightBookmarkRepository

interface AppContainer{
    val flightAirportRepository: FlightAirportRepository
    val flightBookmarkRepository: FlightBookmarkRepository
}

private const val SEARCH_PREFERENCE_NAME="search_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name= SEARCH_PREFERENCE_NAME
)

class AppDataContainer(private val context: Context) :AppContainer {
    override val flightAirportRepository: FlightAirportRepository by lazy{
        OfflineFlightAirportRepository(
            airportDao= FlightDatabase.getDatabase(context).airportDao(),
            dataStore = context.dataStore
        )
    }
    override val flightBookmarkRepository: FlightBookmarkRepository by lazy{
        OfflineFlightBookmarkRepository(
            bookmarkDao= FlightDatabase.getDatabase(context).bookmarkDao()
        )
    }
}