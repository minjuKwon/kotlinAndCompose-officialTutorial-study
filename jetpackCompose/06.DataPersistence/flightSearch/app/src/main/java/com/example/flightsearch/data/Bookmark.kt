package com.example.flightsearch.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="favorite")
data class Bookmark(
    @PrimaryKey
    val id:Int=1,
    @ColumnInfo(name="departure_code")
    val departureCode:String,
    @ColumnInfo(name="destination_code")
    val destinationCode:String
)
