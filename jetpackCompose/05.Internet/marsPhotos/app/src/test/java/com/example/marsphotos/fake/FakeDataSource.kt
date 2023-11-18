package com.example.marsphotos.fake

import com.example.marsphotos.network.MarsPhoto

object FakeDataSource {

    const val idOne="img1"
    const val idTwo="img2"
    const val imgOne="url.l"
    const val imgTwo="url.2"
    val photoList= listOf(
        MarsPhoto(
            id= idOne,
            imgSrc = imgOne
        ),
        MarsPhoto(
            id= idTwo,
            imgSrc= imgTwo
        )
    )
}