package com.example.bookshelf

import com.example.bookshelf.data.NetworkBookshelfRepository
import com.example.bookshelf.fake.FakeBookshelfApiService
import com.example.bookshelf.fake.FakeDataSource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkBookshelfRepositoryTest {

    @Test
    fun networkBookshelfRepository_getBookListInformation_verityItem()=
        runTest {
            val repository= NetworkBookshelfRepository(
                bookshelfApiService = FakeBookshelfApiService()
            )
            assertEquals(FakeDataSource.item,
                repository.getBookListInformation("android",0,0)
            )
        }
}