package com.cafeapp.ui.screens.cart.make_order.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cafeapp.domain.orders.models.DeliveryMethod
import com.cafeapp.domain.orders.models.PaymentMethod
import com.cafeapp.ui.common.ui_components.NetworkErrorComponent
import com.cafeapp.ui.common.ui_components.SomeErrorComponent
import com.cafeapp.ui.screens.cart.make_order.states.MakeOrderScreenState
import com.cafeapp.ui.screens.cart.make_order.states.ui.MakeOrderScreenDataState
import com.cafeapp.ui.screens.cart.make_order.states.ui.MakeOrderScreenLoadingState

@Composable
fun MakeOrderScreenContent(
    state: MakeOrderScreenState,
    modifier: Modifier = Modifier,
    onPaymentMethodSelected: (PaymentMethod) -> Unit = {},
    onDeliveryMethodSelected: (DeliveryMethod) -> Unit = {},
    onDeliveryAddressUpdated: (String) -> Unit = {},
    onRefresh: () -> Unit = {},
    onCheckout: () -> Unit = {}
) {
    when (state) {
        is MakeOrderScreenState.ScreenData -> {
            MakeOrderScreenDataState(
                paymentMethods = state.paymentMethods,
                deliveryMethods = state.deliveryMethods,
                total = state.total,
                isCheckoutButtonAvailable = state.isCheckoutButtonAvailable,
                onPaymentMethodSelected = onPaymentMethodSelected,
                onDeliveryMethodSelected = onDeliveryMethodSelected,
                onDeliveryAddressUpdated = onDeliveryAddressUpdated,
                onCheckout = onCheckout,
                modifier = modifier
            )
        }

        is MakeOrderScreenState.Loading -> {
            MakeOrderScreenLoadingState(modifier = modifier)
        }

        is MakeOrderScreenState.NetworkError -> {
            NetworkErrorComponent(modifier = modifier, onRefresh = onRefresh)
        }

        is MakeOrderScreenState.OtherError -> {
            SomeErrorComponent(modifier = modifier, onRefresh = onRefresh)
        }
    }
}