package com.example.bookshelf.ui

import com.example.bookshelf.network.Item

sealed interface BookshelfUiState{
    data class Success(
        //val list : List<Item> = emptyList(),
        //val list:Item,
        val list :Item
       // val isShowingHomepage: Boolean = true
    ):BookshelfUiState
    object Error : BookshelfUiState
    object Loading: BookshelfUiState
}
