package com.example.bookshelf.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bookshelf.ui.screens.BookshelfHomeScreen

@Composable
fun BookshelfApp(modifier:Modifier= Modifier){
    val bookshelfViewModel = BookshelfViewModel()
    BookshelfHomeScreen(bookshelfViewModel.bookshelfUiState)
}