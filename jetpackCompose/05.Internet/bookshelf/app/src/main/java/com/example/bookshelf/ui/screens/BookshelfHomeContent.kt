package com.example.bookshelf.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.checkBookmarkList
import com.example.bookshelf.checkCurrentItem
import com.example.bookshelf.checkTabPressed
import com.example.bookshelf.data.BookType
import com.example.bookshelf.network.Book
import com.example.bookshelf.ui.BookshelfUiState
import com.example.bookshelf.ui.BookshelfViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfListOnlyContent(
    books:List<Book>,
    bookshelfUiState: BookshelfUiState,
    viewModel: BookshelfViewModel,
    modifier:Modifier= Modifier
){
    var input by remember{mutableStateOf("")}
    Column(
        modifier= Modifier
            .padding(dimensionResource(R.dimen.list_only_content_column_padding)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = input,
            onValueChange = {input=it},
            label={Text(stringResource(R.string.search_label))},
            leadingIcon = {
                Icon(
                    imageVector= Icons.Filled.Search,
                    contentDescription=stringResource(R.string.search),
                    modifier= Modifier
                        .clickable { viewModel.getInformation(input) }
                        .padding(
                            dimensionResource(R.dimen.list_only_content_search_icon_padding)
                        )
                )
            },
            trailingIcon={
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(R.string.search_close),
                    modifier= Modifier
                        .clickable { input = "" }
                        .padding(
                            dimensionResource(R.dimen.list_only_content_search_icon_padding)
                        )
                )
            },
            keyboardOptions= KeyboardOptions.Default.copy(
                imeAction= ImeAction.Search
            ),
            keyboardActions= KeyboardActions(
                onSearch = {viewModel.getInformation(input)},
            ),
            modifier= Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(
                        R.dimen.list_only_content_search_horizontal_padding
                    )
                )
        )

        LazyColumn(
            modifier= Modifier
                .padding(dimensionResource(R.dimen.list_padding))
                .fillMaxHeight(0.9f)
        ){
            if(checkTabPressed(bookshelfUiState)==BookType.Bookmark){
                items(checkBookmarkList(bookshelfUiState)){
                    BookShelfListItem(book = it, viewModel=viewModel, modifier=modifier)
                }
            }else{
                items(books){
                    BookShelfListItem(book = it, viewModel=viewModel, modifier=modifier)
                }
            }

        }

    }
}

@Composable
fun BookshelfListAndDetailContent(
    books:List<Book>,
    bookshelfUiState: BookshelfUiState,
    viewModel: BookshelfViewModel,
    modifier:Modifier= Modifier
){
    Row(modifier=modifier){
        BookshelfListOnlyContent(
            books = books,
            bookshelfUiState = bookshelfUiState,
            viewModel = viewModel
        )
        BookshelfDetailsScreen(
            book = checkCurrentItem(bookshelfUiState),
            viewModel = viewModel
        )
    }
}

@Composable
fun BookmarkEmptyScreen(modifier:Modifier=Modifier){
    Box(
        modifier=modifier,
        contentAlignment = Alignment.Center
    ){
        Text(
            text=stringResource(R.string.empty_bookmark),
            textAlign=TextAlign.Center
        )
    }
}

@Composable
private fun BookShelfListItem(
    book: Book,
    viewModel: BookshelfViewModel,
    modifier:Modifier=Modifier
){
    Row(
        modifier= Modifier
            .clickable { viewModel.updateDetailsScreenState(book.bookInfo) }
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.list_item_padding))
            .background(
                Color(
                    ContextCompat.getColor(LocalContext.current, R.color.light_gray)
                )
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier=Modifier
                .padding(dimensionResource(R.dimen.list_item_image_padding))
        ){
            book.bookInfo.img?.let {
                AsyncImage(
                    model=ImageRequest.Builder(context=LocalContext.current)
                            .data(it.thumbnail).build(),
                    contentDescription = null,
                    contentScale= ContentScale.FillBounds,
                    modifier= Modifier
                        .height(dimensionResource(R.dimen.list_item_image_height))
                        .width(dimensionResource(R.dimen.list_item_image_width))
                )
            }
        }

        Column(
            modifier=Modifier.padding(dimensionResource(R.dimen.list_item_text_column_padding))
        ) {
            book.bookInfo.title?.let {
                Text(
                    text= it,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier=Modifier.padding(
                        bottom=dimensionResource(R.dimen.list_item_text_padding)
                    )
                )
            }
            Row(modifier=modifier){
               book.bookInfo.authors?.forEach{
                   Text(
                       text="$it ",
                       style=MaterialTheme.typography.bodySmall,
                       modifier=Modifier.padding(
                           bottom=dimensionResource(R.dimen.list_item_text_padding)
                       )
                   )
               }
            }
            book.bookInfo.publisher?.let {
                Text(
                    text= it,
                    style=MaterialTheme.typography.bodySmall,
                    modifier=Modifier.padding(
                        bottom=dimensionResource(R.dimen.list_item_text_padding)
                    )
                )
            }
            book.bookInfo.publishedDate?.let {
                Text(
                    text= it,
                    style=MaterialTheme.typography.bodySmall,
                    modifier=modifier
                )
            }
        }

    }
}




