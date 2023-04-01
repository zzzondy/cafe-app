package com.cafeapp.ui.screens.cart.make_order.states.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.cafeapp.R
import com.cafeapp.core.util.UiText
import com.cafeapp.domain.make_order.models.DeliveryMethod
import com.cafeapp.domain.make_order.models.PaymentMethod

@Composable
fun MakeOrderScreenDataState(
    paymentMethods: List<PaymentMethod>,
    deliveryMethods: List<DeliveryMethod>,
    total: Int,
    isCheckoutButtonAvailable: Boolean,
    modifier: Modifier = Modifier,
    onPaymentMethodSelected: (PaymentMethod) -> Unit = {},
    onDeliveryMethodSelected: (DeliveryMethod) -> Unit = {},
    onDeliveryAddressUpdated: (String) -> Unit = {},
    onCheckout: () -> Unit = {}
) {
    Column(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            item {
                DeliveryMethodsSection(
                    deliveryMethods = deliveryMethods,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onDeliveryMethodSelected = onDeliveryMethodSelected,
                    onDeliveryAddressUpdated = onDeliveryAddressUpdated
                )
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                PaymentMethodsSection(
                    paymentMethods = paymentMethods,
                    onPaymentMethodSelected = onPaymentMethodSelected,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                TotalSection(
                    total = total,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }

        MakeOrderButton(
            enabled = isCheckoutButtonAvailable,
            onClick = onCheckout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp, start = 16.dp, end = 16.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeliveryMethodsSection(
    deliveryMethods: List<DeliveryMethod>,
    modifier: Modifier = Modifier,
    onDeliveryMethodSelected: (DeliveryMethod) -> Unit = {},
    onDeliveryAddressUpdated: (String) -> Unit = {}
) {
    var selectedDeliveryMethod by remember { mutableStateOf(deliveryMethods[0]) }

    var typedDeliveryAddress by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    val focusManager = LocalFocusManager.current

    ElevatedCard(
        modifier = modifier.animateContentSize()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = UiText.StringResource(R.string.delivery_methods).asString(),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .selectableGroup()
                    .animateContentSize()
            ) {
                deliveryMethods.forEach { deliveryMethod ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp)
                            .selectable(
                                selected = selectedDeliveryMethod.id == deliveryMethod.id,
                                role = Role.RadioButton,
                                indication = null,
                                interactionSource = MutableInteractionSource()
                            ) {
                                onDeliveryMethodSelected(deliveryMethod)

                                if (selectedDeliveryMethod.id != deliveryMethod.id) {
                                    typedDeliveryAddress = ""
                                }

                                selectedDeliveryMethod = deliveryMethod
                            }
                    ) {
                        RadioButton(
                            selected = selectedDeliveryMethod.id == deliveryMethod.id,
                            onClick = null
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = deliveryMethod.name,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    if (deliveryMethod.isDelivery && selectedDeliveryMethod.id == deliveryMethod.id) {
                        OutlinedTextField(
                            value = typedDeliveryAddress,
                            onValueChange = {
                                typedDeliveryAddress = it
                                onDeliveryAddressUpdated(typedDeliveryAddress)
                            },
                            singleLine = true,
                            label = {
                                Text(
                                    text = UiText.StringResource(R.string.delivery_address)
                                        .asString()
                                )
                            },
                            trailingIcon = {
                                if (typedDeliveryAddress.isNotEmpty()) {
                                    IconButton(onClick = { typedDeliveryAddress = "" }) {
                                        Icon(
                                            imageVector = Icons.Rounded.Clear,
                                            contentDescription = stringResource(
                                                R.string.clear_image
                                            )
                                        )
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                autoCorrect = false,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                                .focusRequester(focusRequester)
                        )
                    } else if (!deliveryMethod.isDelivery && selectedDeliveryMethod.id == deliveryMethod.id) {
                        Row(
                            modifier = Modifier
                                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = UiText.StringResource(R.string.address).asString(),
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = deliveryMethod.fullAddress,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}


@Composable
private fun PaymentMethodsSection(
    paymentMethods: List<PaymentMethod>,
    modifier: Modifier = Modifier,
    onPaymentMethodSelected: (PaymentMethod) -> Unit = {}
) {
    var selectedPaymentMethod by remember { mutableStateOf(paymentMethods[0]) }

    ElevatedCard(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = UiText.StringResource(R.string.choose_payment_method).asString(),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.selectableGroup()) {
                paymentMethods.forEach { paymentMethod ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp)
                            .selectable(
                                selected = selectedPaymentMethod.id == paymentMethod.id,
                                role = Role.RadioButton,
                                indication = null,
                                interactionSource = MutableInteractionSource()
                            ) {
                                onPaymentMethodSelected(paymentMethod)
                                selectedPaymentMethod = paymentMethod
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedPaymentMethod.id == paymentMethod.id,
                            onClick = null
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = paymentMethod.name,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}


@Composable
private fun TotalSection(total: Int, modifier: Modifier = Modifier) {
    ElevatedCard(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = UiText.StringResource(R.string.total).asString(),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = UiText.StringResource(R.string.rubles, total).asString(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


@Composable
private fun MakeOrderButton(
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text = UiText.StringResource(R.string.make_order).asString())
    }
}