package com.example.flightsearch.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.FlightAirportRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AirportViewModel(
    private val repository: FlightAirportRepository
) : ViewModel() {

    private val _airportUiState = MutableStateFlow(AirportUiState())
    val airportUiState: StateFlow<AirportUiState> = _airportUiState

    fun getAirportItem(keyword:String){
        viewModelScope.launch {
            _airportUiState.value=
                _airportUiState.value.copy(
                item=repository.getAirportStream(keyword)
                    .first()
                    .toItem()
            )
        }
    }

    fun getAirportList(keyword:String){
        viewModelScope.launch {
            val itemList = mutableListOf<Airport>()
            repository.getAirportsListStream(keyword).collect {
                itemList.addAll(it)  }
            _airportUiState.value=
                _airportUiState.value.copy(itemList= itemList)
        }
    }

    fun searchByKeyword(keyword:String){
        viewModelScope.launch {
            val searchList = mutableListOf<Airport>()
            repository.searchByKeywordStream(keyword).collect{
                searchList.addAll(it)
            }
            _airportUiState.value=
                _airportUiState.value.copy(searchList=searchList)
        }
    }

}

data class AirportUiState(
    val itemList:List<Airport> = listOf(),
    val searchList:List<Airport> = listOf(),
    val item:Item = Item()
)

data class Item(
    val name:String="",
    val iataCode:String=""
)

fun Airport.toItem():Item=Item(
    name=name,
    iataCode=iataCode
)