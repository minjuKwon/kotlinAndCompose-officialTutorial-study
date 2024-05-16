package com.example.mycity.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Spot(
    val id:Long,
    val isBookmark:Boolean=false,
    @StringRes val name:Int,
    @StringRes val type:Int,
    @StringRes val location:Int,
    @StringRes val duration:Int,
    @StringRes val description:Int,
    @DrawableRes val img:Int
)
