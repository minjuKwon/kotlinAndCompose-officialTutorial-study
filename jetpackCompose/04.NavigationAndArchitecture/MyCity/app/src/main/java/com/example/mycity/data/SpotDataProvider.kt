package com.example.mycity.data

import com.example.mycity.R

object SpotDataProvider {

    val allSpots = listOf(

        Spot(
            id=1L,
            name= R.string.food_1_name,
            type=R.string.food_1_type,
            location = R.string.food_1_location,
            duration = R.string.food_1_duration,
            description = R.string.food_1_description,
            img=R.drawable.restaurant_every_salad,
            spotType = SpotType.Food
        ),
        Spot(
            id=2L,
            name= R.string.food_2_name,
            type=R.string.food_2_type,
            location = R.string.food_2_location,
            duration = R.string.food_2_duration,
            description = R.string.food_2_description,
            img=R.drawable.restaurant_nutty_nutty,
            spotType = SpotType.Food
        ),
        Spot(
            id=3L,
            name= R.string.food_3_name,
            type=R.string.food_3_type,
            location = R.string.food_3_location,
            duration = R.string.food_3_duration,
            description = R.string.food_3_description,
            img=R.drawable.restaurant_strongest_protein,
            spotType = SpotType.Food
        ),
        Spot(
            id=4L,
            name= R.string.food_4_name,
            type=R.string.food_4_type,
            location = R.string.food_4_location,
            duration = R.string.food_4_duration,
            description = R.string.food_4_description,
            img=R.drawable.restaurant_sweet_fruit,
            spotType = SpotType.Food
        ),
        Spot(
            id=5L,
            name= R.string.food_5_name,
            type=R.string.food_5_type,
            location = R.string.food_5_location,
            duration = R.string.food_5_duration,
            description = R.string.food_5_description,
            img=R.drawable.restaurant_fresh_drinks,
            spotType = SpotType.Food
        ),
        Spot(
            id=6L,
            name= R.string.place_1_name,
            type=R.string.place_1_type,
            location = R.string.place_1_location,
            duration = R.string.place_1_duration,
            description = R.string.place_1_description,
            img=R.drawable.place_whizzing_grand_park,
            spotType = SpotType.Joy
        ),
        Spot(
            id=7L,
            name= R.string.place_2_name,
            type=R.string.place_2_type,
            location = R.string.place_2_location,
            duration = R.string.place_2_duration,
            description = R.string.place_2_description,
            img=R.drawable.place_flying,
            spotType = SpotType.Joy
        ),
        Spot(
            id=8L,
            name= R.string.place_3_name,
            type=R.string.place_3_type,
            location = R.string.place_3_location,
            duration = R.string.place_3_duration,
            description = R.string.place_3_description,
            img=R.drawable.place_earth_square,
            spotType = SpotType.Joy
        ),
        Spot(
            id=9L,
            name= R.string.place_4_name,
            type=R.string.place_4_type,
            location = R.string.place_4_location,
            duration = R.string.place_4_duration,
            description = R.string.place_4_description,
            img=R.drawable.place_sky_old_tree,
            spotType = SpotType.Joy
        ),
        Spot(
            id=10L,
            name= R.string.place_5_name,
            type=R.string.place_5_type,
            location = R.string.place_5_location,
            duration = R.string.place_5_duration,
            description = R.string.place_5_description,
            img=R.drawable.place_flower_park,
            spotType = SpotType.Joy
        )

    )

    fun updateIsBookmark(id:Long){
        allSpots.forEach {
            if(it.id == id){
                it.isBookmark= !it.isBookmark
            }
        }
    }

    val defaultSpot = Spot(id=-1, img = R.drawable.baseline_error_outline_24)

}