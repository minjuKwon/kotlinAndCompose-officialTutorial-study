package com.example.bookshelf.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.bookshelf.R
import com.example.bookshelf.ui.BookshelfUiState
import com.example.bookshelf.ui.BookshelfViewModel

@Composable
fun BookshelfHomeScreen(
    bookshelfUiState: BookshelfUiState,
    viewModel: BookshelfViewModel,
    modifier:Modifier=Modifier
){
    when(bookshelfUiState){
        is BookshelfUiState.Success -> {
            if(bookshelfUiState.isShowingHomepage) {BookshelfAppContent(bookshelfUiState, viewModel,modifier)}
            else {BookshelfDetailsScreen(bookshelfUiState.currentItem,viewModel,modifier)}
        }
        else ->{BookshelfAppContent(bookshelfUiState,viewModel,modifier)}
    }
}

@Composable
private fun BookshelfAppContent(
    bookshelfUiState: BookshelfUiState,
    viewModel: BookshelfViewModel,
    modifier:Modifier=Modifier
){
    when(bookshelfUiState){
        is BookshelfUiState.Success -> BookshelfListOnlyContent(
            books=bookshelfUiState.list.book,
            viewModel= viewModel,
            modifier=modifier
            )
        is BookshelfUiState.Loading -> {LoadingScreen(modifier=Modifier.fillMaxSize())}
        is BookshelfUiState.Error -> { ErrorScreen(retryAction = viewModel::getInformation,modifier=Modifier.fillMaxSize())}
    }
}

@Composable
private fun LoadingScreen(modifier: Modifier=Modifier){
    Image(
        painter=painterResource(R.drawable.baseline_rotate_left_24),
        contentDescription=stringResource(R.string.loading),
        modifier=modifier.size(dimensionResource(R.dimen.loading_screen_image_size))
    )
}

@Composable
private fun ErrorScreen(
    retryAction:()->Unit,
    modifier: Modifier=Modifier
){
    Column(
        modifier=modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(R.drawable.baseline_error_outline_24),
            contentDescription = stringResource(R.string.error)
        )
        Text(
            text=stringResource(R.string.error),
            modifier=Modifier.padding(dimensionResource(R.dimen.error_screen_text_padding))
        )
        Button(onClick = retryAction) {
            Text(text=stringResource(R.string.retry))
        }
    }
}