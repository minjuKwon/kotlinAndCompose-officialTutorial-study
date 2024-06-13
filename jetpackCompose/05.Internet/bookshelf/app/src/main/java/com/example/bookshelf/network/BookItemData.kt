package com.example.bookshelf.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    @SerialName(value="items")
    val book:List<Book>
)

@Serializable
data class Book(
   val id:String,
   @SerialName(value="volumeInfo")
   val bookInfo: BookInfo
)

@Serializable
data class BookInfo(
    val title:String,
    val authors:List<String>,
    val publisher:String,
    val publishedDate:String,
    val description:String,
//    @SerialName(value="imageLinks")
//    val img:Image
)

@Serializable
data class Image(
    val thumbnail:String,
    val small:String,
    val medium:String,
    val large:String,
    val smallThumbnail:String,
)