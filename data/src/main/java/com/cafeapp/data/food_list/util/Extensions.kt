package com.cafeapp.data.food_list.util

import com.cafeapp.data.food_list.remote.models.RemoteFood
import com.cafeapp.domain.models.Food

fun RemoteFood.toFoodDomain() = Food(
    id, name, price, description, imageUrl
)