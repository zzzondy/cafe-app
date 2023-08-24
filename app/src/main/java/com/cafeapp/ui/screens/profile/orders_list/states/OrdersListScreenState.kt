package com.cafeapp.ui.screens.profile.orders_list.states

import com.cafeapp.domain.orders.orders_list.models.Order

sealed class OrdersListScreenState {
    object Loading : OrdersListScreenState()

    data class Data(val orders: List<Order>) : OrdersListScreenState()

    object EmptyOrdersList : OrdersListScreenState()

    object NetworkError : OrdersListScreenState()

    object OtherError : OrdersListScreenState()
}
