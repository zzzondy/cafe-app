package com.cafeapp.ui.screens.cart.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cafeapp.R
import com.cafeapp.core.network.rememberNetworkStatus
import com.cafeapp.core.util.UiText
import com.cafeapp.core.util.collectAsEffect
import com.cafeapp.ui.common.ui_components.NetworkErrorComponent
import com.cafeapp.ui.common.ui_components.NetworkWarningComponent
import com.cafeapp.ui.common.ui_components.SomeErrorComponent
import com.cafeapp.ui.screens.cart.CartNavGraph
import com.cafeapp.ui.screens.cart.main.components.CartScreenTopAppBar
import com.cafeapp.ui.screens.cart.main.components.MakeOrderBar
import com.cafeapp.ui.screens.cart.main.states.CartScreenEffect
import com.cafeapp.ui.screens.cart.main.states.CartScreenEvent
import com.cafeapp.ui.screens.cart.main.states.CartScreenState
import com.cafeapp.ui.screens.cart.main.states.ui.EmptyCartScreenState
import com.cafeapp.ui.screens.cart.main.states.ui.IsLoadingScreenState
import com.cafeapp.ui.screens.cart.main.states.ui.NotEmptyCartScreenState
import com.cafeapp.ui.screens.destinations.MakeOrderScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@CartNavGraph(start = true)
@Destination(style = CartScreenTransitions::class)
@Composable
fun CartScreen(
    navigator: DestinationsNavigator,
    cartScreenViewModel: CartScreenViewModel = hiltViewModel()
) {
    val screenState by cartScreenViewModel.cartScreenState.collectAsState()
    val currentTotal by cartScreenViewModel.currentTotal.collectAsState()
    cartScreenViewModel.cartScreenEffect.collectAsEffect { effect ->
        when (effect) {
            is CartScreenEffect.NavigateToMakeOrderScreen -> {
                navigator.navigate(
                    MakeOrderScreenDestination(
                        selectedFoodIds = effect.selectedItemsIds.toLongArray(),
                        total = effect.total
                    )
                )
            }

            else -> {}
        }
    }

    val listState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val networkStatus by rememberNetworkStatus()

    LaunchedEffect(key1 = Unit) {
        cartScreenViewModel.onEvent(CartScreenEvent.ScreenEntered)
    }

    Scaffold(
        topBar = {
            CartScreenTopAppBar(
                title = UiText.StringResource(R.string.cart).asString(),
                scrollBehavior = scrollBehavior,
                onDeleteSelected = { cartScreenViewModel.onEvent(CartScreenEvent.DeleteSelected) }
            )
        },
        modifier = Modifier.padding(bottom = 80.dp)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (screenState) {
                    is CartScreenState.EmptyFoodList -> {
                        EmptyCartScreenState(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                                .padding(horizontal = 16.dp)
                        )
                    }

                    is CartScreenState.FoodList -> {
                        NotEmptyCartScreenState(
                            listState = listState,
                            items = (screenState as CartScreenState.FoodList).foodList,
                            modifier = Modifier
                                .weight(1f)
                                .nestedScroll(scrollBehavior.nestedScrollConnection),
                            onSelectItem = { food ->
                                cartScreenViewModel.onEvent(CartScreenEvent.ItemSelected(food))
                            },
                            onDecrementItemsCount = { id ->
                                cartScreenViewModel.onEvent(CartScreenEvent.DecrementItemsCount(id))
                            },
                            onIncrementItemsCount = { id ->
                                cartScreenViewModel.onEvent(CartScreenEvent.IncrementItemsCount(id))
                            },
                            onDeleteFoodFromCart = { food ->
                                cartScreenViewModel.onEvent(CartScreenEvent.DeleteFoodFromCart(food))
                            }
                        )
                    }

                    is CartScreenState.IsLoading -> {
                        IsLoadingScreenState(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                        )
                    }

                    is CartScreenState.NetworkError -> {
                        NetworkErrorComponent(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize(),
                            onRefresh = { cartScreenViewModel.onEvent(CartScreenEvent.OnRefresh) }
                        )
                    }

                    is CartScreenState.OtherError -> {
                        SomeErrorComponent(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize(),
                            onRefresh = { cartScreenViewModel.onEvent(CartScreenEvent.OnRefresh) }
                        )
                    }
                }

                MakeOrderBar(
                    currentTotal = currentTotal,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onMakeOrder = { cartScreenViewModel.onEvent(CartScreenEvent.MakeOrderClicked) }
                )
            }

            NetworkWarningComponent(
                networkStatus = networkStatus,
                modifier = Modifier.padding(end = 16.dp, bottom = 96.dp)
            )
        }
    }
}
