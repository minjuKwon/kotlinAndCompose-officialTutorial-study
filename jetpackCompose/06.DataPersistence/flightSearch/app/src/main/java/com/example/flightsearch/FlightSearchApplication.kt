package com.example.flightsearch

import android.app.Application
import com.example.flightsearch.data.AppContainer
import com.example.flightsearch.data.AppDataContainer

class FlightSearchApplication : Application() {

    lateinit var appContainer : AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppDataContainer(this)
    }

}