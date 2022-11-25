package com.cafeapp.data.food_list.remote.models

data class FoodRemote(
    val id: Long,
    val name: String,
    val description: String,
    val price: Int,
    val imageUrl: String
)
