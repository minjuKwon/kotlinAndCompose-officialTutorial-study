package com.example.busschedule.data

import android.app.Application

class AppContainer: Application() {
    val database: BusDatabase by lazy{BusDatabase.getDatabase(this)}
}