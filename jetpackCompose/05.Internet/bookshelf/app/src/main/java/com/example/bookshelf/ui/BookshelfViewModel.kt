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
        Log.d("dd","start viewModel getInformation")
        viewModelScope.launch {
            Log.d("dd","start getInformation launch")
            //bookshelfUiState = BookshelfUiState.Loading
            bookshelfUiState = try{
                BookshelfUiState.Success(bookshelfRepository.getBookListInformation(search))
            }catch (e: IOException){
                BookshelfUiState.Error
            }
            Log.d("dd","end getInformation launch")
        }
        Log.d("dd","end viewModel getInformation ")
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

    fun updateDetailsScreenState(){
//        bookshelfUiState =
//            BookshelfUiState.Success(isShowingHomepage = false)
    }

    fun resetHomeScreenState(){
//        bookshelfUiState =
//            BookshelfUiState.Success(isShowingHomepage = true)
    }

}