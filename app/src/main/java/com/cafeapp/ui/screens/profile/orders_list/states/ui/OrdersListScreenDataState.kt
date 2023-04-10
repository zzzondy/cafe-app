package com.cafeapp.ui.screens.profile.orders_list.states.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cafeapp.domain.orders.orders_list.models.Order
import com.cafeapp.ui.screens.profile.orders_list.OrderItem

@Composable
fun OrdersListScreenDataState(orders: List<Order>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
    ) {
        items(orders) { order ->
            OrderItem(order = order)
        }
    }
}