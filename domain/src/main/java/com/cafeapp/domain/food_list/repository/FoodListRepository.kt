package com.cafeapp.domain.food_list.repository

import androidx.paging.PagingData
import com.cafeapp.domain.models.Food
import kotlinx.coroutines.flow.Flow

interface FoodListRepository {
    fun getPagedFoodList(): Flow<PagingData<Food>>
}