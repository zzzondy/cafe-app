package com.cafeapp.domain.cart.usecase

import com.cafeapp.domain.models.Food

class CalculateTotalUseCase {

    operator fun invoke(foodList: List<Pair<Food, Int>>): Int = foodList.sumOf { it.first.price * it.second }
}