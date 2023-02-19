package com.cafeapp.ui.screens.food_list.main


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.cafeapp.R
import com.cafeapp.core.util.UiText
import com.cafeapp.domain.models.Food
import com.cafeapp.ui.screens.food_list.FoodListNavGraph
import com.cafeapp.ui.screens.food_list.main.states.FoodListEvent
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@FoodListNavGraph(start = true)
@Destination
@Composable
fun FoodListScreen(
    foodListViewModel: FoodListViewModel = hiltViewModel()
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(text = UiText.StringResource(R.string.food_list).asString())
                },
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = Modifier
            .padding(bottom = 80.dp)
    ) { paddingValues ->
        FoodList(
            foodList = foodListViewModel.food.collectAsLazyPagingItems(),
            modifier = Modifier
                .padding(paddingValues)
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            onEvent = foodListViewModel::onEvent
        )
    }
}

@Composable
fun FoodList(
    foodList: LazyPagingItems<Food>,
    modifier: Modifier = Modifier,
    onEvent: (FoodListEvent) -> Unit
) {
    foodList.apply {
        when (loadState.refresh) {
            is LoadState.Loading -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(FoodListConfig.GRID_CELLS_COUNT),
                    modifier = modifier.fillMaxSize(),
                    userScrollEnabled = false
                ) {
                    items(FoodListConfig.PLACEHOLDER_ELEMENTS_COUNT, key = { it }) {
                        FoodItem(
                            food = null, modifier = Modifier
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
            is LoadState.Error -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    ErrorWhenRefresh()
                }
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(FoodListConfig.GRID_CELLS_COUNT),
                    modifier = modifier.fillMaxSize()
                ) {
                    items(foodList.itemCount, key = { foodList[it]?.id ?: Unit }) { index ->
                        FoodItem(
                            food = foodList[index],
                            onAddToCart = { onEvent(FoodListEvent.AddFoodToCart(foodList[index]!!.id)) }
                        )
                    }

                    foodList.apply {
                        when (loadState.append) {
                            is LoadState.Loading -> {
                                item(span = { GridItemSpan(this.maxLineSpan) }) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                16.dp
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                            is LoadState.Error -> {
                                item(span = { GridItemSpan(this.maxLineSpan) }) {
                                    ErrorWhenAppend()
                                }
                            }
                            else -> {}
                        }
                    }
                }

            }
        }
    }
}

private object FoodListConfig {
    const val GRID_CELLS_COUNT = 2
    const val PLACEHOLDER_ELEMENTS_COUNT = 4
}