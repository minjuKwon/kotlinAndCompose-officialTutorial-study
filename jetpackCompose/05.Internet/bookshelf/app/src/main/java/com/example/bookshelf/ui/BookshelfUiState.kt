package com.example.bookshelf.ui

import com.example.bookshelf.network.Book

sealed interface BookshelfUiState{
    data class Success(
        val list : List<Book> = emptyList(),
        val isShowingHomepage: Boolean = true
    ):BookshelfUiState
}
