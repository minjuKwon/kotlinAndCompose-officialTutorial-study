package com.example.dessertclicker.data

import androidx.annotation.DrawableRes
import com.example.dessertclicker.data.Datasource.dessertList

data class DesertUiState(
    val revenue: Int=0,
    val dessertsSold: Int=0,
    val currentDessertIndex: Int=0,
    val currentDessertPrice: Int=dessertList[currentDessertIndex].price,
    @DrawableRes val currentDesertImageId: Int=dessertList[currentDessertIndex].imageId
)
