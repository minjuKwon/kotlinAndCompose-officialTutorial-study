package com.example.mycity.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Spot(
    val id:Long,
    @StringRes val name:Int=-1,
    @StringRes val type:Int=-1,
    @StringRes val location:Int=-1,
    @StringRes val duration:Int=-1,
    @StringRes val description:Int=-1,
    @DrawableRes val img:Int,
    val spotType:SpotType=SpotType.Food,
    var isBookmark:Boolean=false
)
