package com.example.flightsearch.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="airport")
data class Airport(
    @PrimaryKey
    val id:Int=1,
    @ColumnInfo(name="iata_code")
    val iataCode:String,
    val name:String,
    val passengers:Int
)
