package com.example.flightsearch.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.flightsearch.data.database.AirportDao
import com.example.flightsearch.data.model.Airport
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class OfflineFlightAirportRepository @Inject constructor(
    private val airportDao: AirportDao,
    private val dataStore: DataStore<Preferences>
) : FlightAirportRepository {

    private companion object{
        val SEARCH_WORD= stringPreferencesKey("search_word")
        const val TAG="SearchPreferencesOfflineRepo"
    }

    override fun getAirportStream(keyword: String): Flow<Airport>
    =airportDao.getAirport(keyword)

    override fun getAirportsListStream(keyword: String): Flow<List<Airport>>
    = airportDao.getAirportsList(keyword)

    override fun searchByKeywordStream(keyword: String): Flow<List<Airport>>
    = airportDao.searchByKeyword(keyword)

    override suspend fun saveSearchWordPreference(searchWord:String){
        dataStore.edit{ preferences->
            preferences[SEARCH_WORD]=searchWord
        }
    }

    override val searchWord:Flow<String> = dataStore.data
        .catch {
            if(it is IOException){
                Log.e(TAG,"Error reading preferences $it")
                emit(emptyPreferences())
            }else{
                throw it
            }
        }.map { preferences->
            preferences[SEARCH_WORD]?:""
        }

}