package com.cafeapp.data.food_list.util

import com.cafeapp.data.food_list.remote.models.FoodRemote
import com.cafeapp.domain.models.Food

fun FoodRemote.toFoodDomain() = Food(
    id, name, price, description, imageUrl
)