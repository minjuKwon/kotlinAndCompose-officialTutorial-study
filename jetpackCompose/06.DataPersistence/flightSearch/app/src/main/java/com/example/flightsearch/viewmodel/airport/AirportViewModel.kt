package com.example.flightsearch.viewmodel.airport

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.data.model.Airport
import com.example.flightsearch.data.repository.FlightAirportRepository
import com.example.flightsearch.viewmodel.checkAirportScreenState
import com.example.flightsearch.viewmodel.checkAirportUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AirportViewModel(
    private val repository: FlightAirportRepository
) : ViewModel() {

    private val _airportUiState = MutableStateFlow<AirportUiState>(AirportUiState.EmptySearch)
    val airportUiState: StateFlow<AirportUiState> = _airportUiState

    fun getAirportList(){
        _airportUiState.value= AirportUiState.Loading
        viewModelScope.launch {
            _airportUiState.value=try{
                val keyword=repository.searchWord.first()
                if(keyword.isEmpty()) AirportUiState.EmptySearch
                else{
                    val item=repository.getAirportStream(keyword).first().toItem()
                    val itemList = repository.getAirportsListStream(keyword).first()
                    checkAirportScreenState(
                        airportUiState= _airportUiState.value,
                        searchingUiState = { _airportUiState.value},
                        resultUiState = {
                            AirportUiState.SearchResult(
                                itemList= itemList,
                                item=item,
                                searchText = keyword
                            )
                        }
                    )
                }
            }catch(e:Exception){AirportUiState.Error}
        }
    }

    fun searchByKeyword(){
        _airportUiState.value= AirportUiState.Loading
        viewModelScope.launch {
            _airportUiState.value=try{
                val keyword=repository.searchWord.first()
                if(keyword.isEmpty()) AirportUiState.EmptySearch
                else{
                    val searchList = repository.searchByKeywordStream(keyword).first()
                    checkAirportScreenState(
                        airportUiState=_airportUiState.value,
                        searchingUiState = {
                            AirportUiState.Searching(searchList=searchList, searchText = keyword)
                        },
                        resultUiState = { _airportUiState.value }
                    )
                }
            }catch(e:Exception){AirportUiState.Error}
        }
    }

    fun saveKeyword(keyword:String){
        viewModelScope.launch {
            repository.saveSearchWordPreference(keyword)
        }
    }

}

data class Item(
    val name:String="",
    val iataCode:String=""
)

fun Airport.toItem(): Item = Item(
    name=name,
    iataCode=iataCode
)