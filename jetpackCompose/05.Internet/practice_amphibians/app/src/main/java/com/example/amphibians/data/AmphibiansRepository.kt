package com.example.amphibians.data

import com.example.amphibians.network.Amphibians
import com.example.amphibians.network.AmphibiansApiService

interface AmphibiansRepository{
    suspend fun getAmphibiansInformation() : List<Amphibians>
}

class NetworkAmphibiansRepository(
    private val amphibiansApiService: AmphibiansApiService
): AmphibiansRepository{
    override suspend fun getAmphibiansInformation(): List<Amphibians> = amphibiansApiService.getInformation()
}