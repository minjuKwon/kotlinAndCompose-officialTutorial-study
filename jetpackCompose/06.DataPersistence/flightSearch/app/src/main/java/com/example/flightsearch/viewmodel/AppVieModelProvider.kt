package com.example.flightsearch.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication
import com.example.flightsearch.viewmodel.airport.AirportViewModel
import com.example.flightsearch.viewmodel.bookmark.BookmarkViewModel

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