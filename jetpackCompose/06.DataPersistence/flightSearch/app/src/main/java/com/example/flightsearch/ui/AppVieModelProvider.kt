package com.example.flightsearch.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication

object AppVieModelProvider {
    val Factory = viewModelFactory {
        initializer{
            AirportViewModel(
                flightSearchApplication().appContainer.flightAirportRepository
            )
        }
        initializer{
            BookmarkViewModel(
                flightSearchApplication().appContainer.flightBookmarkRepository
            )
        }
    }
}

fun CreationExtras.flightSearchApplication():FlightSearchApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
            as FlightSearchApplication)