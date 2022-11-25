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
import androidx.navigation.NavOptionsBuilder
import com.cafeapp.ui.theme.CafeAppTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun FoodList(navigator: DestinationsNavigator) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(text = "asdad")
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(scrolledContainerColor = MaterialTheme.colorScheme.surfaceVariant),
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.padding(paddingValues)) {
            items(100) {
                Text(text = it.toString())
            }
        }
    }
}

@Preview
@Composable
fun FoodListPreview() {
    CafeAppTheme {
        val mockNavigator = object : DestinationsNavigator {
            override fun clearBackStack(route: String): Boolean {
                TODO("Not yet implemented")
            }

            override fun navigate(
                route: String,
                onlyIfResumed: Boolean,
                builder: NavOptionsBuilder.() -> Unit
            ) {
                TODO("Not yet implemented")
            }

            override fun navigateUp(): Boolean {
                TODO("Not yet implemented")
            }

            override fun popBackStack(): Boolean {
                TODO("Not yet implemented")
            }

            override fun popBackStack(
                route: String,
                inclusive: Boolean,
                saveState: Boolean
            ): Boolean {
                TODO("Not yet implemented")
            }
        }
        FoodList(navigator = mockNavigator)
    }
}