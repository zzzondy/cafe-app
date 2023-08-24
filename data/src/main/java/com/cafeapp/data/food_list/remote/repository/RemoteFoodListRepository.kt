package com.cafeapp.data.food_list.remote.repository

import com.cafeapp.data.food_list.remote.models.RemoteFood

interface RemoteFoodListRepository {
    suspend fun getPagedFoodList(page: Int, limit: Int): List<RemoteFood>
}