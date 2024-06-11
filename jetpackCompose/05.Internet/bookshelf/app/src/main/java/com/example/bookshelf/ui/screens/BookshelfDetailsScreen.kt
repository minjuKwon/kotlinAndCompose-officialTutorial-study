package com.example.bookshelf.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.network.Book

@Composable
fun BookshelfDetailsScreen(book: Book){
    Column {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(context= LocalContext.current)
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
        Text(text=book.description)
    }

}