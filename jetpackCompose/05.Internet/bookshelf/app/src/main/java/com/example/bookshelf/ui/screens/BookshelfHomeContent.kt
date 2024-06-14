package com.example.bookshelf.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.network.Book
import com.example.bookshelf.network.BookInfo
import com.example.bookshelf.network.Item
import com.example.bookshelf.ui.BookshelfViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfListOnlyContent(
    books:List<Book>,
    viewModel: BookshelfViewModel,
    modifier:Modifier= Modifier
){
    var input by remember{mutableStateOf("")}
    Column {

        Row(modifier=modifier) {
            OutlinedTextField(
                value = input,
                onValueChange = {input=it}
            )
            Button(onClick = { viewModel.getInformation(input) }){
                Text(
                    text=stringResource(R.string.search)
                )
            }
        }

        LazyColumn{
            items(books){
                BookShelfListItem(book = it)
            }
        }

    }
}

@Composable
fun BookshelfListAndDetailContent(

){


}

@Composable
private fun BookShelfListItem(book: Book){
    Row {
        AsyncImage(
            model = book.bookInfo.img?.let {
                ImageRequest.Builder(context=LocalContext.current)
                .data(it.thumbnail).build()},
            contentDescription = null
        )
        Column {
            book.bookInfo.title?.let { Text(text= it) }
            Row{
               book.bookInfo.authors?.forEach{Text(text=it)}
            }
            book.bookInfo.publisher?.let { Text(text= it) }
            book.bookInfo.description?.let { Text(text= it) }
            book.bookInfo.publishedDate?.let { Text(text= it) }
            Spacer(modifier=Modifier.fillMaxWidth().height(30.dp))
        }
    }
}
