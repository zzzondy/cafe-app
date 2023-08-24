package com.cafeapp.ui.screens.profile.orders_list.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cafeapp.ui.common.ui_components.NetworkErrorComponent
import com.cafeapp.ui.common.ui_components.SomeErrorComponent
import com.cafeapp.ui.screens.profile.orders_list.states.OrdersListScreenState
import com.cafeapp.ui.screens.profile.orders_list.states.ui.OrdersListScreenDataState
import com.cafeapp.ui.screens.profile.orders_list.states.ui.OrdersListScreenEmptyState
import com.cafeapp.ui.screens.profile.orders_list.states.ui.OrdersListScreenLoadingState

@Composable
fun OrdersListScreenContent(
    screenState: OrdersListScreenState,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit = {}
) {
    when (screenState) {
        is OrdersListScreenState.Loading -> {
            OrdersListScreenLoadingState(modifier = modifier.padding(horizontal = 16.dp))
        }

        is OrdersListScreenState.Data -> {
            OrdersListScreenDataState(
                orders = screenState.orders,
                modifier = modifier.padding(horizontal = 16.dp)
            )
        }

        is OrdersListScreenState.NetworkError -> {
            NetworkErrorComponent(modifier = modifier, onRefresh = onRefresh)
        }

        is OrdersListScreenState.OtherError -> {
            SomeErrorComponent(modifier = modifier, onRefresh = onRefresh)
        }

        is OrdersListScreenState.EmptyOrdersList -> {
            OrdersListScreenEmptyState(modifier = modifier)
        }
    }
}