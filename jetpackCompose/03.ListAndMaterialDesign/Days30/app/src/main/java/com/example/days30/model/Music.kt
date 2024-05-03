package com.example.days30.model

import androidx.annotation.DrawableRes

data class Music(
    @DrawableRes val imageResourceId:Int,
    val title:Int,
    val artist:Int,
    val genre:Int,
    val duration:Int
)
