package com.example.flightsearch

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FlightSearchApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}