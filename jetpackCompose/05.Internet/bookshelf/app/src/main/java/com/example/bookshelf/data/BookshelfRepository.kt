package com.example.bookshelf.data

import com.example.bookshelf.network.BookshelfApiService
import com.example.bookshelf.network.Item

interface BookshelfRepository {
    suspend fun getBookListInformation(query:String):Item
}

class NetworkBookshelfRepository(
    private val bookshelfApiService: BookshelfApiService
):BookshelfRepository{
    override suspend fun getBookListInformation(query:String): Item
    = bookshelfApiService.getInformation(query)
}