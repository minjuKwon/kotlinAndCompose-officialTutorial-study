package com.example.bookshelf.network

data class Book(
    val title:String,
    val authors:List<String>,
    val publisher:String,
    val publishedDate:String,
    val description:String,
    val img:String
)
