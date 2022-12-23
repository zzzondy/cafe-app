package com.cafeapp.ui.screens.food_list


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.cafeapp.R
import com.cafeapp.domain.models.Food
import com.cafeapp.ui.util.UiText
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun FoodListScreen(
    navigator: DestinationsNavigator,
    foodListViewModel: FoodListViewModel = hiltViewModel()
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(text = UiText.StringResource(R.string.food_list).asString())
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(scrolledContainerColor = MaterialTheme.colorScheme.surfaceVariant),
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        FoodList(foodList = foodListViewModel.food, modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun FoodList(foodList: Flow<PagingData<Food>>, modifier: Modifier = Modifier) {
    val items = foodList.collectAsLazyPagingItems()
    val listState = rememberLazyGridState()
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyVerticalGrid(
            state = listState,
            columns = GridCells.Fixed(FoodListConfig.GRID_CELLS_COUNT),
            modifier = Modifier.wrapContentHeight()
        ) {
            items(items.itemCount) { index: Int ->
                FoodItem(food = items[index]!!)
            }

            items.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        items(FoodListConfig.PLACEHOLDER_ITEMS_COUNT) {
                            FoodItem(food = null)
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        item(span = { GridItemSpan(this.maxLineSpan) }) {
                            ErrorWhenAppend()
                        }
                    }

                }
            }
        }
        items.apply {
            when {
                loadState.refresh is LoadState.Error -> {
                    ErrorWhenRefresh()
                }
            }
        }
    }
}

private object FoodListConfig {
    const val GRID_CELLS_COUNT = 2
    const val PLACEHOLDER_ITEMS_COUNT = 4
}