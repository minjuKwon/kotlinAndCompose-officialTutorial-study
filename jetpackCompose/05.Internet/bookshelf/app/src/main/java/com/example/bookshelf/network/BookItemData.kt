package com.example.bookshelf.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("items")
data class Book(
   val id:Int,
   val bookInfo: BookInfo
)

@SerialName("volumeInfo")
data class BookInfo(
    val title:String,
    val authors:List<String>,
    val publisher:String,
    val publishedDate:String,
    val description:String,
    val img:Image
)

@SerialName("imageLinks")
data class Image(
    val thumbnail:String,
    val small:String,
    val medium:String,
    val large:String,
    val smallThumbnail:String,
)