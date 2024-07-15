package com.example.flightsearch.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {

    @Query("SELECT iata_code,name FROM airport WHERE iata_code =:keyword")
    fun getAirport(keyword:String): Flow<Airport>

    @Query("SELECT iata_code,name FROM airport " +
            "WHERE iata_code != :keyword ORDER BY passengers DESC")
    fun getAirportsList(keyword: String): Flow<List<Airport>>

    @Query("SELECT iata_code,name FROM airport " +
            "WHERE iata_code LIKE '%'||:keyword||'%' OR name LIKE '%'||:keyword||'%'")
    fun searchByKeyword(keyword:String): Flow<List<Airport>>

}