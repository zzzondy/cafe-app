package com.cafeapp.data.food_list.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cafeapp.data.food_list.paging.FoodListPagingSource
import com.cafeapp.data.food_list.remote.repository.RemoteFoodListRepository
import com.cafeapp.domain.models.Food
import com.cafeapp.domain.food_list.repository.FoodListRepository
import kotlinx.coroutines.flow.Flow

class FoodListRepositoryImpl(private val remoteFoodListRepository: RemoteFoodListRepository) :
    FoodListRepository {
    override fun getPagedFoodList(): Flow<PagingData<Food>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE, prefetchDistance = PAGE_SIZE),
            pagingSourceFactory = { FoodListPagingSource(remoteFoodListRepository) }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}
