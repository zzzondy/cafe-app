package com.cafeapp.data.food_list.repository

import androidx.paging.PagingData
import com.cafeapp.domain.models.Food
import kotlinx.coroutines.flow.Flow
import com.cafeapp.domain.food_list.repository.FoodListRepository

class FoodListRepository : FoodListRepository {
    override fun getPagedFoodList(): Flow<PagingData<Food>> {
        TODO()
    }
}