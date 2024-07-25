package com.example.flightsearch.viewmodel.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.data.model.Bookmark
import com.example.flightsearch.data.repository.FlightBookmarkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val repository: FlightBookmarkRepository
):ViewModel() {

    private val _bookmarkUiState = MutableStateFlow<BookmarkUiState>(BookmarkUiState.Empty)
    val bookmarkUiState:StateFlow<BookmarkUiState> = _bookmarkUiState

    suspend fun insertItem(item: Bookmark){
        _bookmarkUiState.value = BookmarkUiState.Loading
        _bookmarkUiState.value = try {
            repository.insertBookmarkData(item)
            BookmarkUiState.Success()
        }catch (e:Exception) {BookmarkUiState.Error}
    }

    fun deleteItem(item: Bookmark){
        _bookmarkUiState.value=BookmarkUiState.Loading
        viewModelScope.launch {
            try{
                repository.deleteBookmarkData(item)
                if(repository.getBookmarkDataCount()==0){
                    _bookmarkUiState.value=BookmarkUiState.Empty
                }
                else {getAllBookmarks()}
            }catch (e:Exception){BookmarkUiState.Error}
        }
    }

    fun getAllBookmarks(){
        _bookmarkUiState.value=BookmarkUiState.Loading
        viewModelScope.launch {
            _bookmarkUiState.value=try{
                BookmarkUiState.Success(
                    repository.getAllBookmarkStream()?.firstOrNull()?: emptyList()
                )
            }catch (e:Exception){BookmarkUiState.Error}
        }
    }

}
