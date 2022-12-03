package com.cafeapp.ui.screens.food_list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavOptionsBuilder
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.cafeapp.domain.models.Food
import com.cafeapp.ui.theme.CafeAppTheme
import com.cafeapp.R
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
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = modifier) {
        items(items.itemCount) { index: Int ->
            FoodItem(food = items[index]!!)
        }
    }
}