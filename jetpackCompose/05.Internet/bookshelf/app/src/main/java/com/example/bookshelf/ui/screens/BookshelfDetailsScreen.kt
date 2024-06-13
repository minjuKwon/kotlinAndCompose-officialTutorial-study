package com.example.bookshelf.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.network.Item

//@Composable
//fun BookshelfDetailsScreen(item: Item){
//    Column {
//        Row {
//            AsyncImage(
//                model = ImageRequest.Builder(context= LocalContext.current)
//                    .data(book.bookInfo.img.thumbnail),
//                contentDescription = null
//            )
//            Column {
//                Text(text=item.book.bookInfo.title)
//                Row{
//                    for(author in item.book.bookInfo.authors){
//                        Text(text=author)
//                    }
//                }
//                Text(text=item.book.bookInfo.publisher)
//                Text(text=item.book.bookInfo.publishedDate)
//            }
//        }
//        Text(text=item.book.bookInfo.description)
//    }
//
//}