package com.example.bookshelf.fake

import com.example.bookshelf.network.BookshelfApiService
import com.example.bookshelf.network.Item

class FakeBookshelfApiService :BookshelfApiService {
    override suspend fun getInformation(query: String, count: Int, startIndex: Int): Item {
        return FakeDataSource.item
    }
}