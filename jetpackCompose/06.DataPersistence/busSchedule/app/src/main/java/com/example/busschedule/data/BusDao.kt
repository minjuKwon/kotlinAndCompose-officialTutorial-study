package com.example.busschedule.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BusDao {

    @Query("SELECT * FROM schedule WHERE stop_name=:stopName")
    fun getSchedule(stopName: String): Flow<List<BusSchedule>>

    @Query("SELECT * FROM schedule ORDER BY arrival_time")
    fun getAllSchedule(): Flow<List<BusSchedule>>

}