package com.example.bookshelf.network

import retrofit2.http.GET
import retrofit2.http.Query

interface BookshelfApiService {
    
    @GET("volumes")
    suspend fun getInformation(@Query("q")query:String):Item
/*
    @GET("volumes")
    suspend fun getInformation(
        @Query("q") query:String
    ):List<Item>
*/
}
