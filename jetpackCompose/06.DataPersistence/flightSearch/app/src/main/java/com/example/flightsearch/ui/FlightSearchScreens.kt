package com.example.flightsearch.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flightsearch.R
import com.example.flightsearch.data.model.Bookmark
import com.example.flightsearch.viewmodel.airport.AirportUiState
import com.example.flightsearch.viewmodel.airport.AirportViewModel
import com.example.flightsearch.viewmodel.bookmark.BookmarkUiState
import com.example.flightsearch.viewmodel.bookmark.BookmarkViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FlightSearchScreen(
    modifier:Modifier=Modifier,
    airPortViewModel:AirportViewModel = hiltViewModel(),
    bookmarkViewModel:BookmarkViewModel = hiltViewModel()
){
    val airportUiState by airPortViewModel.airportUiState.collectAsState()
    val bookmarkUiState by bookmarkViewModel.bookmarkUiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    var text by remember{mutableStateOf(TextFieldValue(airPortViewModel.searchingText))}

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val currentFocusRequester by remember{mutableStateOf(focusRequester)}

    when(airportUiState){
        is AirportUiState.Loading->{
            LoadingScreen(modifier=modifier)
        }
        is AirportUiState.Error->{
            ErrorScreen(
                onHomeScreenChange={ airPortViewModel
                        .updateAirportUiState(AirportUiState.EmptySearch())},
                onSearchScreenChange={ airPortViewModel
                    .updateAirportUiState(AirportUiState.Searching())},
                modifier=modifier
            )
        }
        is AirportUiState.Searching->{
            val searchingUiState = (airportUiState as AirportUiState.Searching)
            if(text.text.isEmpty()){
                airPortViewModel.updateAirportUiState(AirportUiState.EmptySearch())
                keyboardController?.hide()
            }else{
                SearchingScreen(
                    searchQuery = text,
                    onSearchQueryChange={
                        text=it
                        airPortViewModel.updateText(it.text)
                        airPortViewModel.searchByKeyword()
                    },
                    items= searchingUiState.searchList,
                    onSearch={airPortViewModel.getAirportList(it)},
                    onReset={
                        airPortViewModel.updateAirportUiState(AirportUiState.EmptySearch())
                        text=it
                    },
                    screenModifier=modifier,
                    textFieldModifier=Modifier.focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            if (!focusState.isFocused) {
                                currentFocusRequester.requestFocus()
                            }
                        }
                )
            }
        }
        is AirportUiState.SearchResult->{
            val searchResultUiState = (airportUiState as AirportUiState.SearchResult)
            SearchResultScreen(
                searchQuery=text,
                onSearchQueryChange = {
                    text=it
                    airPortViewModel.updateText(it.text)
                    airPortViewModel.searchByKeyword()
                },
                listTitle=stringResource(R.string.result_list_title),
                items=searchResultUiState.itemList,
                item=searchResultUiState.item,
                onSearch={airPortViewModel.getAirportList(it)},
                onReset={
                    airPortViewModel.updateAirportUiState(AirportUiState.EmptySearch())
                    text=it
                },
                onInsert={coroutineScope.launch {bookmarkViewModel.insertItem(it)}},
                onDelete={bookmarkViewModel.deleteItem(it)},
                modifier=modifier
            )
        }
        is AirportUiState.EmptySearch->{
            val emptyUiState = (airportUiState as AirportUiState.EmptySearch)
            CheckBookmarkUiStateScreen(
                bookmarkUiState=bookmarkUiState,
                bookmarkViewModel = bookmarkViewModel,
                airPortViewModel=airPortViewModel,
                text=text,
                emptyScreenMode = emptyUiState.mode,
                onTextChange={
                    text=it
                    airPortViewModel.updateText(it.text)
                    airPortViewModel.searchByKeyword()
                },
                onSearch={airPortViewModel.getAirportList(it)},
                onReset = { text=it },
                onInsert={coroutineScope.launch {bookmarkViewModel.insertItem(it)}},
                onDelete={bookmarkViewModel.deleteItem(it)},
                modifier=modifier
            )
        }
    }

}

@Composable
fun CheckBookmarkUiStateScreen(
    bookmarkUiState: BookmarkUiState,
    bookmarkViewModel:BookmarkViewModel,
    airPortViewModel: AirportViewModel,
    text:TextFieldValue,
    emptyScreenMode:Int,
    onTextChange:(TextFieldValue)->Unit,
    onSearch:(String)->Unit,
    onReset: (TextFieldValue) -> Unit,
    onInsert:(Bookmark)->Unit,
    onDelete:(Bookmark)->Unit,
    modifier: Modifier=Modifier
){
    when(bookmarkUiState){
        is BookmarkUiState.Loading->{
            LoadingScreen(modifier=modifier)
        }
        is BookmarkUiState.Success->{
            bookmarkViewModel.getAllBookmarks()
            BookmarkScreen(
                searchQuery = text,
                onSearchQueryChange = {
                    onTextChange(it)
                },
                listTitle=stringResource(R.string.bookmark_list_title),
                emptyScreenMode=emptyScreenMode,
                items= bookmarkUiState.itemList,
                onSearch= {onSearch(it)},
                onReset= onReset,
                onInsert=onInsert,
                onDelete=onDelete,
                modifier=modifier
            )
        }
        is BookmarkUiState.Error->{
            ErrorScreen(
                onHomeScreenChange={ airPortViewModel
                    .updateAirportUiState(AirportUiState.EmptySearch())},
                onSearchScreenChange={ airPortViewModel
                    .updateAirportUiState(AirportUiState.Searching())}
                ,modifier=modifier
            )
        }
    }
}