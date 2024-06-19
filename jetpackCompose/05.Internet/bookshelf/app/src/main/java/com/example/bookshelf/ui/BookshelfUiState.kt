package com.example.bookshelf.ui

import com.example.bookshelf.data.BookType
import com.example.bookshelf.network.Book
import com.example.bookshelf.network.BookInfo
import com.example.bookshelf.network.Image
import com.example.bookshelf.network.Item

sealed interface BookshelfUiState{
    data class Success(
        val list :Item= Item(emptyList()),
        val bookmarkList:MutableList<Book> = mutableListOf(),
        val currentTabType:BookType=BookType.Books,
        val currentItem : BookInfo= defaultBookInfo,
        val isShowingHomepage: Boolean = true
    ):BookshelfUiState
    object Error : BookshelfUiState
    object Loading: BookshelfUiState
}

val defaultBookInfo
= BookInfo("", emptyList(),"","","",
    Image("","","","")
)