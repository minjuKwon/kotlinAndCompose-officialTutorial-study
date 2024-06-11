package com.example.bookshelf.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bookshelf.network.Book
import com.example.bookshelf.ui.BookshelfUiState

@Composable
fun BookshelfHomeScreen(
    bookshelfUiState: BookshelfUiState,
    modifier:Modifier=Modifier
){
    when(bookshelfUiState){
        is BookshelfUiState.Success -> {
            if(bookshelfUiState.isShowingHomepage) {BookshelfAppContent(bookshelfUiState)}
            else {BookshelfDetailsScreen(book = bookshelfUiState.list[0])}
        }

    }
}

@Composable
private fun BookshelfAppContent(
    bookshelfUiState: BookshelfUiState,
    modifier:Modifier=Modifier
){
    when(bookshelfUiState){
        is BookshelfUiState.Success ->  BookshelfListOnlyContent(bookshelfUiState.list)
    }

}