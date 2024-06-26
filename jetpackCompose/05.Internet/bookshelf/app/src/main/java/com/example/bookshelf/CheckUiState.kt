package com.example.bookshelf

import androidx.paging.PagingData
import com.example.bookshelf.data.BookType
import com.example.bookshelf.network.Book
import com.example.bookshelf.network.BookInfo
import com.example.bookshelf.ui.BookshelfUiState
import com.example.bookshelf.ui.defaultBookInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

fun checkTabPressed(
    bookshelfUiState: BookshelfUiState
): BookType
{
    return checkUiState(
        bookshelfUiState=bookshelfUiState,
        a={it.currentTabType},
        b={BookType.Books}
    )
}

fun checkBookmarkIsEmpty(
    bookshelfUiState: BookshelfUiState
):Boolean
{
    return checkUiState(
        bookshelfUiState=bookshelfUiState,
        a={it.bookmarkList.isEmpty()},
        b={false}
    )
}

fun checkBookmarkList(
    bookshelfUiState: BookshelfUiState
):MutableList<Book>
{
    return checkUiState(
        bookshelfUiState=bookshelfUiState,
        a={it.bookmarkList},
        b={ mutableListOf()}
    )
}

fun checkCurrentItem(
    bookshelfUiState: BookshelfUiState
):BookInfo
{
    return checkUiState(
        bookshelfUiState=bookshelfUiState,
        a={it.currentItem},
        b={ defaultBookInfo}
    )
}

fun checkBookList(
    bookshelfUiState: BookshelfUiState
): Flow<PagingData<Book>>
{
    return checkUiState(
        bookshelfUiState=bookshelfUiState,
        a={it.list.book},
        b={ MutableStateFlow(PagingData.from(emptyList())) }
    )
}

fun getTotalItemsCount(
    bookshelfUiState: BookshelfUiState
):Int{
    return checkUiState(
        bookshelfUiState=bookshelfUiState,
        a={it.list.totalCount},
        b={0}
    )
}

fun <T>checkUiState(
    bookshelfUiState: BookshelfUiState,
    a:(BookshelfUiState.Success)->T,
    b:()->T
):T{
    return when(bookshelfUiState){
        is BookshelfUiState.Success->a(bookshelfUiState)
        else->b()
    }
}