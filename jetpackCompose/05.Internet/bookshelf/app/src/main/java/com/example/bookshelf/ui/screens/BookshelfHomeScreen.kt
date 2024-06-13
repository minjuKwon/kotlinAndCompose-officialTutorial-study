package com.example.bookshelf.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bookshelf.ui.BookshelfUiState
import com.example.bookshelf.ui.BookshelfViewModel

@Composable
fun BookshelfHomeScreen(
    bookshelfUiState: BookshelfUiState,
    viewModel: BookshelfViewModel,
    modifier:Modifier=Modifier
){
    BookshelfAppContent(bookshelfUiState, viewModel)
//    when(bookshelfUiState){
//        is BookshelfUiState.Success -> {
//            if(bookshelfUiState.isShowingHomepage) {BookshelfAppContent(bookshelfUiState, viewModel)}
//            else {BookshelfDetailsScreen(item = bookshelfUiState.list[0])}
//        }
//        else ->{}
//    }
}

@Composable
private fun BookshelfAppContent(
    bookshelfUiState: BookshelfUiState,
    viewModel: BookshelfViewModel,
    modifier:Modifier=Modifier
){
    when(bookshelfUiState){
        is BookshelfUiState.Success -> BookshelfListOnlyContent(
            books=bookshelfUiState.list.book,
            viewModel= viewModel
            )
        is BookshelfUiState.Loading -> {}
        is BookshelfUiState.Error -> {}
    }

}