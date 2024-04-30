package com.example.listandmaterialdesign.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Topic(
    @StringRes val titleResourceId: Int,
    val count: Int,
    @DrawableRes val imageResourceId: Int
)
