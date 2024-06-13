package com.example.bookshelf.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelf.ui.screens.BookshelfHomeScreen

@Composable
fun BookshelfApp(modifier:Modifier= Modifier){
    val bookshelfViewModel: BookshelfViewModel = viewModel(factory=BookshelfViewModel.Factory)
    BookshelfHomeScreen(
        bookshelfUiState=bookshelfViewModel.bookshelfUiState,
        viewModel = bookshelfViewModel
    )
}