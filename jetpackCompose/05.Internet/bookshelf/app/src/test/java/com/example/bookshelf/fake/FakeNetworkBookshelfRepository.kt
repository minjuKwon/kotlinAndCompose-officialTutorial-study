package com.example.bookshelf.fake

import com.example.bookshelf.data.BookshelfRepository
import com.example.bookshelf.network.Item

class FakeNetworkBookshelfRepository :BookshelfRepository {
    override suspend fun getBookListInformation(query: String, count: Int, startIndex: Int): Item {
        return FakeDataSource.item
    }
}