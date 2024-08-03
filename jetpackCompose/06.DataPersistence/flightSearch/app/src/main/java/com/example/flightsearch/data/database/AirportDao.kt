package com.example.flightsearch.data.database

import androidx.room.Dao
import androidx.room.Query
import com.example.flightsearch.data.model.Airport
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {

    @Query("SELECT * FROM airport WHERE iata_code = UPPER(:keyword)")
    fun getAirport(keyword:String): Flow<Airport>

    @Query("SELECT * FROM airport " +
            "WHERE iata_code != UPPER(:keyword) ORDER BY passengers DESC")
    fun getAirportsList(keyword: String): Flow<List<Airport>>

    @Query("SELECT * FROM airport " +
            "WHERE iata_code LIKE '%'||UPPER(:keyword)||'%' OR name LIKE '%'||:keyword||'%'")
    fun searchByKeyword(keyword:String): Flow<List<Airport>>

}