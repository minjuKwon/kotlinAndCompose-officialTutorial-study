package com.example.bookshelf.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.network.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfListOnlyContent(
    books:List<Book>,
    modifier:Modifier= Modifier
){
    var input by remember{mutableStateOf("")}
    Column {
        
        Row(modifier=modifier) {
            OutlinedTextField(
                value = input,
                onValueChange = {input=it}
            )
            Button(onClick = {}){
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
            model = ImageRequest.Builder(context=LocalContext.current)
                .data(book.img),
            contentDescription = null
        )
        Column {
            Text(text=book.title)
            Row{
                for(author in book.authors){
                    Text(text=author)
                }
            }
            Text(text=book.publisher)
            Text(text=book.publishedDate)
        }
    }
}
