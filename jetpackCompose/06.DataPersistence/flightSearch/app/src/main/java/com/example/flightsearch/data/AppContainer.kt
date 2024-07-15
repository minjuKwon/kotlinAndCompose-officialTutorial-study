package com.example.flightsearch.data

import android.content.Context

interface AppContainer{
    val flightRepository:FlightRepository
}

class AppDataContainer(private val context: Context) :AppContainer {
    override val flightRepository:FlightRepository by lazy{
        OfflineFlightRepository(
            airportDao=FlightDatabase.getDatabase(context).airportDao(),
            bookmarkDao=FlightDatabase.getDatabase(context).bookmarkDao()
        )
    }
}