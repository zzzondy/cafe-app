package com.cafeapp.ui.screens.cart.make_order

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cafeapp.core.util.collectAsEffect
import com.cafeapp.ui.common.ui_components.LoadingDialog
import com.cafeapp.ui.screens.cart.CartNavGraph
import com.cafeapp.ui.screens.cart.make_order.components.MakeOrderScreenContent
import com.cafeapp.ui.screens.cart.make_order.components.MakeOrderScreenSuccessDialog
import com.cafeapp.ui.screens.cart.make_order.components.MakeOrderScreenTopAppBar
import com.cafeapp.ui.screens.cart.make_order.states.MakeOrderScreenEffect
import com.cafeapp.ui.screens.cart.make_order.states.MakeOrderScreenEvent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@CartNavGraph
@Destination(
    navArgsDelegate = MakeOrderScreenNavArgs::class,
    style = MakeOrderScreenTransitions::class
)
@Composable
fun MakeOrderScreen(
    navigator: DestinationsNavigator,
    makeOrderScreenViewModel: MakeOrderScreenViewModel = hiltViewModel()
) {
    val screenState by makeOrderScreenViewModel.screenState.collectAsState()
    var isVisibleLoadingDialog by remember { mutableStateOf(false) }
    var isVisibleSuccessDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarScope = rememberCoroutineScope()
    val context = LocalContext.current

    makeOrderScreenViewModel.screenEffect.collectAsEffect { effect ->
        when (effect) {
            is MakeOrderScreenEffect.NavigateBack -> navigator.popBackStack()

            is MakeOrderScreenEffect.ShowLoading -> isVisibleLoadingDialog = true

            is MakeOrderScreenEffect.HideLoading -> isVisibleLoadingDialog = false

            is MakeOrderScreenEffect.ShowSnackbar -> {
                snackbarScope.launch {
                    snackbarHostState.showSnackbar(message = effect.message.asString(context))
                }
            }

            is MakeOrderScreenEffect.ShowSuccessDialog -> isVisibleSuccessDialog = true
        }
    }


    if (isVisibleLoadingDialog) {
        LoadingDialog()
    }

    if (isVisibleSuccessDialog) {
        MakeOrderScreenSuccessDialog(
            onDismissRequest = {
                makeOrderScreenViewModel.onEvent(
                    MakeOrderScreenEvent.OnBackButtonPressed
                )
            }
        )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = data.visuals.message,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        },
        topBar = {
            MakeOrderScreenTopAppBar(
                onBackPressed = {
                    makeOrderScreenViewModel.onEvent(
                        MakeOrderScreenEvent.OnBackButtonPressed
                    )
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        MakeOrderScreenContent(
            state = screenState,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            onRefresh = { makeOrderScreenViewModel.onEvent(MakeOrderScreenEvent.OnRefreshClicked) },
            onPaymentMethodSelected = { paymentMethod ->
                makeOrderScreenViewModel.onEvent(
                    MakeOrderScreenEvent.PaymentMethodSelected(paymentMethod)
                )
            },
            onDeliveryMethodSelected = { deliveryMethod ->
                makeOrderScreenViewModel.onEvent(
                    MakeOrderScreenEvent.DeliveryMethodSelected(
                        deliveryMethod
                    )
                )
            },
            onDeliveryAddressUpdated = {
                makeOrderScreenViewModel.onEvent(MakeOrderScreenEvent.DeliveryAddressUpdated(it))
            },
            onCheckout = {
                makeOrderScreenViewModel.onEvent(MakeOrderScreenEvent.OnCheckoutClicked)
            }
        )
    }
}