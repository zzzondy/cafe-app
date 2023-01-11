package com.cafeapp.ui.screens.food_list


import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
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
import com.cafeapp.domain.models.Food
import com.cafeapp.ui.util.AnimationsConst
import com.cafeapp.ui.util.UiText
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun FoodListScreen(
    navigator: DestinationsNavigator,
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
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FoodList(foodList: LazyPagingItems<Food>, modifier: Modifier = Modifier) {
    foodList.apply {
        when (loadState.refresh) {
            is LoadState.Loading -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
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
                    items(foodList.itemCount, key = { foodList[it]?.id ?: "1" }) { index ->
                        FoodItem(
                            food = foodList[index],
                            modifier = Modifier.animateItemPlacement(tween(AnimationsConst.transitionsDuration))
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
}