package com.example.bookshelf

import com.example.bookshelf.fake.FakeNetworkBookshelfRepository
import com.example.bookshelf.rules.TestDispatcherRule
import com.example.bookshelf.ui.BookshelfUiState
import com.example.bookshelf.ui.BookshelfViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class BookshelfViewModelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun bookshelfViewModel_getBookListInformation_verifyBookshelfUiStateSuccess()=
        runTest {

            val bookshelfViewModel=BookshelfViewModel(
                bookshelfRepository = FakeNetworkBookshelfRepository()
            )

            assertEquals(BookshelfUiState.Loading, bookshelfViewModel.bookshelfUiState)

        }
}

