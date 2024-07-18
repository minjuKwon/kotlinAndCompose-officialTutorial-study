package com.example.flightsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.data.Bookmark
import com.example.flightsearch.data.FlightBookmarkRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class BookmarkViewModel(
    private val repository: FlightBookmarkRepository
):ViewModel() {

    val bookmarkUiState: StateFlow<BookmarkUiState>
        =repository.getAllBookmarkStream().map { BookmarkUiState(it) }
        .stateIn(
            scope=viewModelScope,
            started= SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue=BookmarkUiState()
        )

    companion object{
        private const val TIMEOUT_MILLIS=5_000L
    }

    suspend fun insertItem(item:Bookmark){
        repository.insertBookmarkData(item)
    }

    suspend fun deleteItem(item: Bookmark){
        repository.deleteBookmarkData(item)
    }

}

data class BookmarkUiState(
    val itemList:List<Bookmark> = listOf(),
)
