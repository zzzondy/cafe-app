package com.cafeapp.ui.screens.food_list.main


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.cafeapp.R
import com.cafeapp.core.network.rememberNetworkStatus
import com.cafeapp.core.util.UiText
import com.cafeapp.domain.models.Food
import com.cafeapp.ui.common.ui_components.NetworkWarningComponent
import com.cafeapp.ui.common.ui_components.SomeErrorComponent
import com.cafeapp.ui.screens.food_list.FoodListNavGraph
import com.cafeapp.ui.screens.food_list.main.states.FoodListEvent
import com.cafeapp.ui.screens.food_list.main.states.ui.FoodListContentState
import com.cafeapp.ui.screens.food_list.main.states.ui.LoadingRefreshFoodScreenState
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@FoodListNavGraph(start = true)
@Destination
@Composable
fun FoodListScreen(
    foodListViewModel: FoodListViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val pagingItems = foodListViewModel.food.collectAsLazyPagingItems()

    val networkStatus by rememberNetworkStatus()

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
            .fillMaxSize()
            .padding(bottom = 80.dp)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues), contentAlignment = Alignment.BottomEnd) {
            FoodListScreenContent(
                foodList = pagingItems,
                modifier = Modifier
                    .fillMaxSize(),
                onEvent = foodListViewModel::onEvent
            )

            NetworkWarningComponent(
                networkStatus = networkStatus,
                modifier = Modifier.padding(16.dp)
            )
        }

    }
}

@Composable
fun FoodListScreenContent(
    foodList: LazyPagingItems<Food>,
    modifier: Modifier = Modifier,
    onEvent: (FoodListEvent) -> Unit
) {
    foodList.apply {
        when (loadState.refresh) {
            is LoadState.Loading -> {
                LoadingRefreshFoodScreenState(modifier = modifier)
            }
            is LoadState.Error -> {
                SomeErrorComponent(modifier = modifier, onRefresh = { foodList.refresh() })
            }
            else -> {
                FoodListContentState(
                    foodListItems = foodList,
                    modifier = modifier,
                    onEvent = onEvent::invoke
                )
            }
        }
    }
}

object FoodListConfig {
    const val GRID_CELLS_COUNT = 2
}