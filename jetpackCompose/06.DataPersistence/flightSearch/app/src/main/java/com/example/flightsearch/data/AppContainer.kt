package com.example.flightsearch.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

interface AppContainer{
    val flightRepository:FlightRepository
}

private const val SEARCH_PREFERENCE_NAME="search_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name= SEARCH_PREFERENCE_NAME
)

class AppDataContainer(private val context: Context) :AppContainer {
    override val flightRepository:FlightRepository by lazy{
        OfflineFlightRepository(
            airportDao=FlightDatabase.getDatabase(context).airportDao(),
            bookmarkDao=FlightDatabase.getDatabase(context).bookmarkDao(),
            dataStore = context.dataStore
        )
    }
}