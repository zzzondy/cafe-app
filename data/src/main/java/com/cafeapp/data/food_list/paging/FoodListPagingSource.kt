package com.cafeapp.data.food_list.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cafeapp.data.food_list.remote.repository.RemoteFoodListRepository
import com.cafeapp.data.food_list.util.toFoodDomain
import com.cafeapp.domain.models.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class FoodListPagingSource(private val remoteFoodListRepository: RemoteFoodListRepository) :
    PagingSource<Int, Food>() {

    override fun getRefreshKey(state: PagingState<Int, Food>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Food> {
        return try {
            withContext(Dispatchers.IO) {
                val currentPage = (params.key ?: 1)
                val data = remoteFoodListRepository.getPagedFoodList(currentPage, params.loadSize)
                    .map { it.toFoodDomain() }

                if (data.isEmpty() && currentPage == 1) {
                    throw IOException()
                }

                LoadResult.Page(
                    data = data,
                    prevKey = if (currentPage == 1) null else currentPage - params.loadSize,
                    nextKey = if (data.isEmpty()) null else currentPage + params.loadSize
                )
            }
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }
    }
}