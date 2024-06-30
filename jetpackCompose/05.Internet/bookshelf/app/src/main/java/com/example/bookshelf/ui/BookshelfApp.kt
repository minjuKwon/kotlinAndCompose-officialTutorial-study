package com.example.bookshelf.ui

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelf.ui.screens.BookshelfHomeScreen
import com.example.bookshelf.ui.utils.ContentType
import com.example.bookshelf.ui.utils.NavigationType

@Composable
fun BookshelfApp(
    windowSize: WindowWidthSizeClass,
    modifier:Modifier= Modifier
){
    val bookshelfViewModel: BookshelfViewModel = viewModel(factory=BookshelfViewModel.Factory)
    val navigationType:NavigationType
    val contentType:ContentType

    val currentPage by bookshelfViewModel.currentPage.collectAsState()
    val scrollState  = rememberLazyListState()

    when(windowSize){
        WindowWidthSizeClass.Compact->{
            navigationType=NavigationType.BOTTOM_NAVIGATION
            contentType=ContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Medium->{
            navigationType=NavigationType.NAVIGATION_RAIL
            contentType=ContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Expanded->{
            navigationType=NavigationType.PERMANENT_NAVIGATION_DRAWER
            contentType=ContentType.LIST_AND_DETAIL
        }
        else->{
            navigationType=NavigationType.BOTTOM_NAVIGATION
            contentType=ContentType.LIST_ONLY
        }
    }

    BookshelfHomeScreen(
        bookshelfUiState=bookshelfViewModel.bookshelfUiState,
        onTabPressed={ bookshelfViewModel.updateCurrentBookTabType(it) },
        onSearch={ bookshelfViewModel.getInformation(it)},
        onBookItemPressed={bookshelfViewModel.updateDetailsScreenState(it)},
        onBackPressed={bookshelfViewModel.resetHomeScreenState()},
        onBookmarkPressed={bookshelfViewModel.updateBookmarkList(it)},
        navigationType = navigationType,
        contentType= contentType,
        currentPage=currentPage,
        updatePage={bookshelfViewModel.getInformation(page=it)},
        modifier=modifier,
        scrollState=scrollState
    )
}