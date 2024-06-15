package com.example.bookshelf.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.network.Book
import com.example.bookshelf.network.BookInfo

@Composable
fun BookshelfDetailsScreen(book: BookInfo){
    Column {
        Row {
            AsyncImage(
                model = book.img?.let {
                    ImageRequest.Builder(context = LocalContext.current)
                        .data(it.thumbnail).build()
                },
                contentDescription = null
            )
            Column {
                book.title?.let { Text(text = it) }
                Row {
                    book.authors?.forEach { Text(text = it) }
                }
                book.publisher?.let { Text(text = it) }
                book.publishedDate?.let { Text(text = it) }
            }
            book.description?.let { Text(text = it) }
        }
    }
}