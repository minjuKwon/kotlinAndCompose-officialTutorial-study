package com.example.flightsearch.viewmodel.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.data.model.Bookmark
import com.example.flightsearch.data.repository.FlightBookmarkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val repository: FlightBookmarkRepository
):ViewModel() {

    private val _bookmarkUiState = MutableStateFlow<BookmarkUiState>(BookmarkUiState.Success())
    val bookmarkUiState:StateFlow<BookmarkUiState> = _bookmarkUiState

    private var isLoading = true

    suspend fun insertItem(item: Bookmark){
        try {
            repository.insertBookmarkData(item)
            isLoading=true
        }catch (e:Exception) {BookmarkUiState.Error}
    }

    fun deleteItem(item: Bookmark){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                repository.deleteBookmarkData(item)
                isLoading=true
                getAllBookmarks()
            }catch (e:Exception){BookmarkUiState.Error}
        }
    }

    fun getAllBookmarks(){
        if(isLoading){//ui 상태 업데이트에 따른 무한 반복 방지
            viewModelScope.launch {
                _bookmarkUiState.value=BookmarkUiState.Loading
                delay(500)
                _bookmarkUiState.value=try{
                    BookmarkUiState.Success(
                        repository.getAllBookmarkStream()?.firstOrNull()?: emptyList()
                    )
                }catch (e:Exception){BookmarkUiState.Error}
            }
        }
        isLoading=false
    }

}
