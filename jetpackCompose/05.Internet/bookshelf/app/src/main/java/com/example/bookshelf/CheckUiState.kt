package com.example.bookshelf

import com.example.bookshelf.data.BookType
import com.example.bookshelf.network.Book
import com.example.bookshelf.network.BookInfo
import com.example.bookshelf.ui.BookshelfUiState
import com.example.bookshelf.ui.defaultBookInfo

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
):List<Book>
{
    return checkUiState(
        bookshelfUiState=bookshelfUiState,
        a={it.list.book},
        b={ emptyList() }
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