package com.example.flightsearch.viewmodel.bookmark

import com.example.flightsearch.data.model.Bookmark

sealed class BookmarkUiState{
    data class Success(val itemList:List<Bookmark>? = listOf()):BookmarkUiState()
    object Error:BookmarkUiState()
    object Loading:BookmarkUiState()
}