package com.cafeapp.ui.screens.profile.orders_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.cafeapp.core.util.collectAsEffect
import com.cafeapp.ui.screens.profile.ProfileNavGraph
import com.cafeapp.ui.screens.profile.orders_list.components.OrdersListScreenContent
import com.cafeapp.ui.screens.profile.orders_list.components.OrdersListScreenTopAppBar
import com.cafeapp.ui.screens.profile.orders_list.states.OrdersListScreenEffect
import com.cafeapp.ui.screens.profile.orders_list.states.OrdersListScreenEvent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@ProfileNavGraph
@Destination(style = OrdersListScreenTransitions::class)
@Composable
fun OrdersListScreen(
    navigator: DestinationsNavigator,
    ordersListScreenViewModel: OrdersListScreenViewModel = hiltViewModel()
) {
    val screenState by ordersListScreenViewModel.screenState.collectAsState()
    ordersListScreenViewModel.screenEffect.collectAsEffect { effect ->
        when (effect) {
            OrdersListScreenEffect.NavigateBack -> navigator.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            OrdersListScreenTopAppBar(
                onBackButtonClicked = { ordersListScreenViewModel.onEvent(OrdersListScreenEvent.OnBackButtonClicked) }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        OrdersListScreenContent(
            screenState = screenState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            onRefresh = {
                ordersListScreenViewModel.onEvent(OrdersListScreenEvent.OnRefresh)
            }
        )
    }
}