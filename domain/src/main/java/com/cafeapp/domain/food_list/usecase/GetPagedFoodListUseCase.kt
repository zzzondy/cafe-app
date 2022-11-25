package com.cafeapp.domain.food_list.usecase

import com.cafeapp.domain.food_list.repository.FoodListRepository

class GetPagedFoodListUseCase(private val foodListRepository: FoodListRepository) {
    operator fun invoke() = foodListRepository.getPagedFoodList()
}