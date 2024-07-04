package com.example.bookshelf.ui

import androidx.paging.PagingData
import com.example.bookshelf.data.BookType
import com.example.bookshelf.network.Book
import com.example.bookshelf.network.BookInfo
import com.example.bookshelf.network.Image
import kotlinx.coroutines.flow.Flow

sealed interface BookshelfUiState{
    data class Success(
        val list :PageData,
        val bookmarkList:MutableList<Book> = mutableListOf(),
        val currentTabType:BookType=BookType.Books,
        val currentItem : MutableMap <BookType,BookInfo> = mutableMapOf(),
        val isShowingHomepage: Boolean = true
    ):BookshelfUiState
    object Error : BookshelfUiState
    object Loading: BookshelfUiState
}

val defaultBookInfo
= BookInfo("", emptyList(),"","","",
    Image("","","","")
)

data class PageData(
    val book: Flow<PagingData<Book>>,
    val totalCount:Int
)