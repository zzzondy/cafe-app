package com.cafeapp.ui.screens.cart.make_order

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.cafeapp.ui.screens.cart.CartNavGraph
import com.cafeapp.ui.screens.cart.make_order.components.MakeOrderScreenContent
import com.cafeapp.ui.screens.cart.make_order.components.MakeOrderScreenTopAppBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@OptIn(ExperimentalMaterial3Api::class)
@CartNavGraph
@Destination(
    navArgsDelegate = MakeOrderScreenNavArgs::class
)
@Composable
fun MakeOrderScreen(
    navigator: DestinationsNavigator,
    makeOrderScreenViewModel: MakeOrderScreenViewModel = hiltViewModel()
) {
    val screenState by makeOrderScreenViewModel.screenState.collectAsState()

    Scaffold(
        topBar = {
            MakeOrderScreenTopAppBar(onBackPressed = { navigator.popBackStack() })
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        MakeOrderScreenContent(
            state = screenState,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        )
    }
}