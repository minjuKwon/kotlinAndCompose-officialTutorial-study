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
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.bookshelf.BookshelfApplication
import com.example.bookshelf.data.BookPagingSource
import com.example.bookshelf.data.BookType
import com.example.bookshelf.data.BookshelfRepository
import com.example.bookshelf.network.Book
import com.example.bookshelf.network.BookInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

const val PAGE_SIZE=10

class BookshelfViewModel(
    private val bookshelfRepository: BookshelfRepository
):ViewModel() {

    var bookshelfUiState: BookshelfUiState by mutableStateOf(BookshelfUiState.Loading)
        private set

    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage

    init {
        getInformation()
    }

    fun getInformation(search:String="android",page:Int=1){

        val pageData: Flow<PagingData<Book>> = Pager(
            config= PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            )){BookPagingSource(bookshelfRepository,input=search,page=page,pageSize=PAGE_SIZE)}
            .flow.cachedIn(viewModelScope)

        viewModelScope.launch {
            bookshelfUiState = try{
                val totalCount=withContext(Dispatchers.IO){
                    bookshelfRepository
                        .getBookListInformation(search,10,0).totalCount
                }
                _currentPage.value=page
                when(bookshelfUiState){
                    is BookshelfUiState.Success-> (bookshelfUiState as BookshelfUiState.Success)
                        .copy(list= PageData(pageData,totalCount))
                    else-> BookshelfUiState.Success(
                        list= PageData(pageData,totalCount)
                    )
                }
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

    private fun updateCopiedUiState(
        uiState:BookshelfUiState,
        copyOperation:(BookshelfUiState.Success)-> BookshelfUiState.Success
    ):BookshelfUiState
    {
        return when(uiState){
            is BookshelfUiState.Success-> copyOperation(uiState)
            else ->uiState
        }
    }

}