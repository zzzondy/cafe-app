package com.cafeapp.data.food_list.remote.repository

import com.cafeapp.data.food_list.remote.models.FoodRemote

interface RemoteFoodListRepository {
    suspend fun getPagedFoodList(page: Int, limit: Int): List<FoodRemote>
}