package com.cafeapp.ui.screens.cart.make_order.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cafeapp.domain.make_order.models.DeliveryMethod
import com.cafeapp.domain.make_order.models.PaymentMethod
import com.cafeapp.ui.screens.cart.make_order.states.MakeOrderScreenState
import com.cafeapp.ui.screens.cart.make_order.states.ui.MakeOrderScreenDataState

@Composable
fun MakeOrderScreenContent(
    state: MakeOrderScreenState,
    modifier: Modifier = Modifier,
    onPaymentMethodSelected: (PaymentMethod) -> Unit = {},
    onDeliveryMethodSelected: (DeliveryMethod) -> Unit = {}
) {
    when (state) {
        is MakeOrderScreenState.ScreenData -> {
            MakeOrderScreenDataState(
                paymentMethods = state.paymentMethods,
                deliveryMethods = state.deliveryMethods,
                total = state.total,
                onPaymentMethodSelected = onPaymentMethodSelected,
                onDeliveryMethodSelected = onDeliveryMethodSelected,
                modifier = modifier
            )
        }

        else -> {}
    }
}