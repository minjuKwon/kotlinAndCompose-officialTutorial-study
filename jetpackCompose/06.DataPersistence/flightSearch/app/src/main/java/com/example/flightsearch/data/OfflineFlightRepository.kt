package com.example.flightsearch.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class OfflineFlightRepository(
    private val airportDao: AirportDao,
    private val bookmarkDao: BookmarkDao,
    private val dataStore: DataStore<Preferences>
) :FlightRepository {

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

    override fun getAllBookmarkStream(): Flow<List<Bookmark>>
    =bookmarkDao.getAllBookmark()

    override suspend fun insertBookmarkData(bookmark: Bookmark)
    =bookmarkDao.insertBookmark(bookmark)

    suspend fun saveSearchWordPreference(searchWord:String){
        dataStore.edit{ preferences->
            preferences[SEARCH_WORD]=searchWord
        }
    }

    val searchWord:Flow<String> = dataStore.data
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