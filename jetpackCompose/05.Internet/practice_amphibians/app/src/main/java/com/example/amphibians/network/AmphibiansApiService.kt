package com.example.amphibians.network

import retrofit2.http.GET

interface AmphibiansApiService {
    @GET("amphibians")
    suspend fun getInformation(): List<Amphibians>
}