package com.example.bookshelf.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.network.BookInfo
import com.example.bookshelf.ui.BookshelfViewModel

@Composable
fun BookshelfDetailsScreen(
    book: BookInfo,
    viewModel:BookshelfViewModel,
    modifier: Modifier =Modifier
){
    BackHandler {
        viewModel.resetHomeScreenState()
    }

    LazyColumn{
        item{
            DetailsScreenContent(book,viewModel,modifier)
        }
    }

}

@Composable
private fun DetailsScreenContent(
    book: BookInfo,
    viewModel:BookshelfViewModel,
    modifier: Modifier =Modifier
){
    Column(modifier=modifier) {

        IconButton(
            onClick = {viewModel.resetHomeScreenState()},
            modifier=modifier
        ) {
            Icon(
                imageVector= Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
                modifier= Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
                    .padding(dimensionResource(R.dimen.detail_screen_arrow_padding))
            )
        }

        Column(
            modifier=Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier=Modifier
                    .padding(dimensionResource(R.dimen.detail_screen_image_padding))
            ){
                book.img?.let {
                    AsyncImage(
                        model=ImageRequest.Builder(context=LocalContext.current)
                            .data(it.thumbnail).build(),
                        error=painterResource(R.drawable.baseline_broken_image_24),
                        placeholder = painterResource(R.drawable.baseline_rotate_left_24),
                        contentDescription = null,
                        modifier= Modifier
                            .height(dimensionResource(R.dimen.detail_screen_image_height))
                    )
                }
            }

            DetailScreenContentInformation(book)

            book.description?.let {
                Text(
                    text = it,
                    style= MaterialTheme.typography.titleSmall,
                    modifier= Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(dimensionResource(R.dimen.detail_screen_text_padding))
                )
            }

        }

    }
}

@Composable
private fun DetailScreenContentInformation(
    book: BookInfo
){
    Column(
        modifier=Modifier
            .padding(dimensionResource(R.dimen.detail_screen_text_column_padding))
    ) {

        book.title?.let {
            Text(
                text= it,
                style = MaterialTheme.typography.titleLarge,
                modifier= Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(dimensionResource(R.dimen.detail_screen_text_padding))
            )
        }

        Row(modifier=Modifier.align(Alignment.End)){
            book.authors?.forEach{
                Text(
                    text="$it ",
                    style= MaterialTheme.typography.titleSmall,
                    modifier=Modifier
                        .padding(
                            end=dimensionResource(R.dimen.detail_screen_text_padding)
                        )
                )
            }
        }

        book.publisher?.let {
            Text(
                text= it,
                style= MaterialTheme.typography.titleSmall,
                modifier= Modifier
                    .align(Alignment.End)
                    .padding(
                        end = dimensionResource(R.dimen.detail_screen_text_padding)
                    )
            )
        }

        book.publishedDate?.let {
            Text(
                text= it,
                style= MaterialTheme.typography.titleSmall,
                modifier= Modifier
                    .align(Alignment.End)
                    .padding(
                        end = dimensionResource(R.dimen.detail_screen_text_padding)
                    )
            )
        }

    }
}