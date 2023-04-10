package com.cafeapp.ui.screens.profile.orders_list.states

sealed interface OrdersListScreenEvent {
    object OnRefresh : OrdersListScreenEvent

    object OnBackButtonClicked : OrdersListScreenEvent
}