package com.cafeapp.ui.screens.food_list.main.states.ui

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cafeapp.ui.screens.food_list.main.FoodItem
import com.cafeapp.ui.screens.food_list.main.FoodListConfig
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder


@Composable
fun LoadingRefreshFoodScreenState(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(FoodListConfig.GRID_CELLS_COUNT),
        modifier = modifier,
        userScrollEnabled = false
    ) {
        items(LoadingRefreshStateConfig.PLACEHOLDER_ELEMENTS_COUNT, key = { it }) {
            FoodItem(
                food = null,
                modifier = Modifier
                    .placeholder(
                        visible = true,
                        color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                        shape = MaterialTheme.shapes.medium,
                        highlight = PlaceholderHighlight.fade()
                    )
            )
        }
    }
}

private object LoadingRefreshStateConfig {
    const val PLACEHOLDER_ELEMENTS_COUNT = 4
}