package com.example.bookshelf.ui

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
import com.example.bookshelf.data.BookType
import com.example.bookshelf.data.BookshelfRepository
import com.example.bookshelf.network.Book
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
                BookshelfUiState.Success(
                    list=bookshelfRepository.getBookListInformation(search,0),
                )
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
        bookshelfUiState=updateCopiedUiState(bookshelfUiState){
            it.copy(currentItem = bookInfo, isShowingHomepage = false)
        }
    }

    fun resetHomeScreenState(){
        bookshelfUiState=updateCopiedUiState(bookshelfUiState){
            it.copy(isShowingHomepage = true)
        }
    }

    fun updateCurrentBookTabType(bookType: BookType){
        bookshelfUiState=updateCopiedUiState(bookshelfUiState){
            it.copy(currentTabType = bookType)
        }
    }

    fun updateBookmarkList(book:Book){
        book.bookInfo.isBookmarked= !book.bookInfo.isBookmarked
        bookshelfUiState=updateCopiedUiState(bookshelfUiState){
            var tempList:MutableList<Book> = it.bookmarkList
            tempList = if(book.bookInfo.isBookmarked){
                (tempList+book) as MutableList<Book>
            }else{
                (tempList-book) as MutableList<Book>
            }
            it.copy(bookmarkList = tempList)
        }
    }

    private fun <T:BookshelfUiState.Success> updateCopiedUiState(
        uiState:BookshelfUiState,
        copyOperation:(BookshelfUiState.Success)->T
    ):BookshelfUiState
    {
        return when(uiState){
            is BookshelfUiState.Success-> copyOperation(uiState)
            else ->uiState
        }
    }

}