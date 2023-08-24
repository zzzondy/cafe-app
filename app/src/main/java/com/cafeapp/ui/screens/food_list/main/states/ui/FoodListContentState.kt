package com.cafeapp.ui.screens.food_list.main.states.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.cafeapp.domain.models.Food
import com.cafeapp.ui.screens.food_list.main.FoodItem
import com.cafeapp.ui.screens.food_list.main.FoodListConfig
import com.cafeapp.ui.screens.food_list.main.states.FoodListEvent

@Composable
fun FoodListContentState(
    foodListItems: LazyPagingItems<Food>,
    modifier: Modifier = Modifier,
    onEvent: (FoodListEvent) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(FoodListConfig.GRID_CELLS_COUNT),
        modifier = modifier
    ) {
        items(foodListItems.itemCount, key = { index -> foodListItems[index]!!.id }) { index ->
            FoodItem(
                food = foodListItems[index],
                onAddToCart = { onEvent(FoodListEvent.AddFoodToCart(foodListItems[index]!!)) }
            )
        }

        item(span = { GridItemSpan(this.maxLineSpan) }) {
            if (foodListItems.loadState.append is LoadState.Loading) {
                AppendLoadingState(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            } else if (foodListItems.loadState.append is LoadState.Error) {
                ErrorWhenAppend(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onRefresh = { foodListItems.retry() }
                )
            }
        }
    }
}