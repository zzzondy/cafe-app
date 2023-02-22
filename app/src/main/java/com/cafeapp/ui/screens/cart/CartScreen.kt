package com.cafeapp.ui.screens.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cafeapp.R
import com.cafeapp.core.util.UiText
import com.cafeapp.ui.common.NetworkErrorState
import com.cafeapp.ui.common.SomeErrorState
import com.cafeapp.ui.screens.cart.components.CartScreenTopAppBar
import com.cafeapp.ui.screens.cart.components.MakeOrderBar
import com.cafeapp.ui.screens.cart.states.CartScreenEffect
import com.cafeapp.ui.screens.cart.states.CartScreenEvent
import com.cafeapp.ui.screens.cart.states.CartScreenState
import com.cafeapp.ui.screens.cart.states.ui.EmptyCartScreenState
import com.cafeapp.ui.screens.cart.states.ui.IsLoadingScreenState
import com.cafeapp.ui.screens.cart.states.ui.NotEmptyCartScreenState
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@CartNavGraph(start = true)
@Destination(style = CartScreenTransitions::class)
@Composable
fun CartScreen(cartScreenViewModel: CartScreenViewModel = hiltViewModel()) {
    val screenState by cartScreenViewModel.cartScreenState.collectAsState()
    val currentTotal by cartScreenViewModel.currentTotal.collectAsState()
    val screenEffect by cartScreenViewModel.cartScreenEffect.collectAsState(initial = CartScreenEffect.Initial)

    val listState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val snackBarHostState = remember { SnackbarHostState() }
    val snackBarScope = rememberCoroutineScope()

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        cartScreenViewModel.onEvent(CartScreenEvent.ScreenEntered)
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            snackBarScope.launch {
                if (screenEffect is CartScreenEffect.ShowNetworkErrorSnackBar) {
                    snackBarHostState.showSnackbar(
                        message = UiText.StringResource(R.string.refresh_error).asString(context)
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            CartScreenTopAppBar(
                title = UiText.StringResource(R.string.cart).asString(),
                scrollBehavior = scrollBehavior,
                onDeleteSelected = { cartScreenViewModel.onEvent(CartScreenEvent.DeleteSelected) }
            )
        },
        modifier = Modifier.padding(bottom = 80.dp)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (screenState) {
                is CartScreenState.EmptyFoodList -> {
                    EmptyCartScreenState(
                        modifier = Modifier
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

                    MakeOrderBar(
                        currentTotal = currentTotal,
                        modifier = Modifier
                            .fillMaxWidth(),
                        onMakeOrder = {}
                    )
                }

                is CartScreenState.IsLoading -> {
                    IsLoadingScreenState(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                is CartScreenState.NetworkError -> {
                    NetworkErrorState(
                        modifier = Modifier.fillMaxSize(),
                        onRefresh = { cartScreenViewModel.onEvent(CartScreenEvent.OnRefresh) }
                    )
                }

                is CartScreenState.OtherError -> {
                    SomeErrorState(
                        modifier = Modifier.fillMaxSize(),
                        onRefresh = { cartScreenViewModel.onEvent(CartScreenEvent.OnRefresh) }
                    )
                }
            }
        }
    }
}
