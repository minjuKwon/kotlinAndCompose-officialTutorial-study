package com.example.bookshelf.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookshelfApplication
import com.example.bookshelf.data.BookshelfRepository
import com.example.bookshelf.network.BookInfo
import kotlinx.coroutines.launch
import java.io.IOException

class BookshelfViewModel(
    private val bookshelfRepository: BookshelfRepository
):ViewModel() {

    var bookshelfUiState: BookshelfUiState by mutableStateOf(BookshelfUiState.Loading)
        private set

    init {
        getInformation()
    }

    fun getInformation(search:String="android"){
        viewModelScope.launch {
            bookshelfUiState = BookshelfUiState.Loading
            bookshelfUiState = try{
                BookshelfUiState.Success(bookshelfRepository.getBookListInformation(search))
            }catch (e: IOException){
                BookshelfUiState.Error
            }
        }
    }

    companion object{
        val Factory : ViewModelProvider.Factory = viewModelFactory{
            initializer{
                val application = (this[APPLICATION_KEY] as BookshelfApplication)
                val bookshelfRepository = application.container.bookshelfRepository
                BookshelfViewModel(bookshelfRepository)
            }
        }
    }

    fun updateDetailsScreenState(bookInfo: BookInfo){
        bookshelfUiState =
            BookshelfUiState.Success(currentItem = bookInfo, isShowingHomepage = false)
    }

    fun resetHomeScreenState(){
        bookshelfUiState =
            BookshelfUiState.Success(isShowingHomepage = true)
    }

}