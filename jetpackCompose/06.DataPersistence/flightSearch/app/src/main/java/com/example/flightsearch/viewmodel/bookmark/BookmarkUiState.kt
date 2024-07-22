package com.example.flightsearch.viewmodel.bookmark

import com.example.flightsearch.data.model.Bookmark

sealed interface BookmarkUiState{
    data class Success(val itemList:List<Bookmark> = listOf()):BookmarkUiState
    object Empty:BookmarkUiState
    object Error:BookmarkUiState
    object Loading:BookmarkUiState
}