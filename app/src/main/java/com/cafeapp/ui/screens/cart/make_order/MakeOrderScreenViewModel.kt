package com.cafeapp.ui.screens.cart.make_order

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeapp.R
import com.cafeapp.core.providers.dispatchers.DispatchersProvider
import com.cafeapp.core.util.UIText
import com.cafeapp.domain.make_order.models.DeliveryMethod
import com.cafeapp.domain.make_order.models.OrderDetails
import com.cafeapp.domain.make_order.models.PaymentMethod
import com.cafeapp.domain.make_order.states.MakeOrderResult
import com.cafeapp.domain.make_order.states.ObtainingDeliveryMethodsResult
import com.cafeapp.domain.make_order.states.ObtainingPaymentMethodsResult
import com.cafeapp.domain.make_order.usecase.MakeOrderUseCase
import com.cafeapp.domain.make_order.usecase.ObtainDeliveryMethodsUseCase
import com.cafeapp.domain.make_order.usecase.ObtainPaymentMethodsUseCase
import com.cafeapp.ui.screens.cart.make_order.states.MakeOrderScreenEffect
import com.cafeapp.ui.screens.cart.make_order.states.MakeOrderScreenEvent
import com.cafeapp.ui.screens.cart.make_order.states.MakeOrderScreenState
import com.cafeapp.ui.screens.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MakeOrderScreenViewModel @Inject constructor(
    private val makeOrderUseCase: MakeOrderUseCase,
    private val obtainPaymentMethodsUseCase: ObtainPaymentMethodsUseCase,
    private val obtainDeliveryMethodsUseCase: ObtainDeliveryMethodsUseCase,
    private val dispatchersProvider: DispatchersProvider,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val selectedFoodItems =
        savedStateHandle.navArgs<MakeOrderScreenNavArgs>().selectedFoodIds.toList()

    private val foodsCount = savedStateHandle.navArgs<MakeOrderScreenNavArgs>().foodsCount.toList()

    private val total = savedStateHandle.navArgs<MakeOrderScreenNavArgs>().total

    private var orderDetails = OrderDetails(food = selectedFoodItems.zip(foodsCount), total = total)

    private val _screenState = MutableStateFlow<MakeOrderScreenState>(MakeOrderScreenState.Loading)
    val screenState = _screenState.asStateFlow()

    private val _screenEffect = MutableSharedFlow<MakeOrderScreenEffect>()
    val screenEffect = _screenEffect.asSharedFlow()

    init {
        screenEntered()
    }

    fun onEvent(event: MakeOrderScreenEvent) {
        when (event) {
            is MakeOrderScreenEvent.OnRefreshClicked -> onRefreshClicked()

            is MakeOrderScreenEvent.OnCheckoutClicked -> onCheckout()

            is MakeOrderScreenEvent.DeliveryMethodSelected -> onDeliveryMethodSelected(event.deliveryMethod)

            is MakeOrderScreenEvent.DeliveryAddressUpdated -> onDeliveryAddressUpdated(event.address)

            is MakeOrderScreenEvent.PaymentMethodSelected -> onPaymentMethodSelected(event.paymentMethod)

            is MakeOrderScreenEvent.OnBackButtonPressed -> onBackButtonPressed()
        }
    }

    private fun onCheckout() {
        viewModelScope.launch(dispatchersProvider.io) {
            _screenEffect.emit(MakeOrderScreenEffect.ShowLoading)
            when (makeOrderUseCase(orderDetails)) {
                MakeOrderResult.Success -> {
                    _screenEffect.emit(MakeOrderScreenEffect.HideLoading)
                    _screenEffect.emit(MakeOrderScreenEffect.ShowSuccessDialog)
                }

                MakeOrderResult.NetworkError -> {
                    _screenEffect.emit(MakeOrderScreenEffect.HideLoading)
                    _screenEffect.emit(MakeOrderScreenEffect.ShowSnackbar(UIText.StringResource(R.string.network_unavailable)))
                }

                MakeOrderResult.OtherError -> {
                    _screenEffect.emit(MakeOrderScreenEffect.HideLoading)
                    _screenEffect.emit(MakeOrderScreenEffect.ShowSnackbar(UIText.StringResource(R.string.some_error)))
                }

                MakeOrderResult.UserNotAuthenticated -> {
                    _screenEffect.emit(MakeOrderScreenEffect.HideLoading)
                    _screenEffect.emit(MakeOrderScreenEffect.ShowSnackbar(UIText.StringResource(R.string.user_not_authenticated)))
                }
            }
        }
    }

    private fun screenEntered() {
        viewModelScope.launch(dispatchersProvider.io) {
            _screenState.update {
                val paymentMethodResult = obtainPaymentMethodsUseCase()
                val deliveryMethodsResult = obtainDeliveryMethodsUseCase()

                when {
                    paymentMethodResult is ObtainingPaymentMethodsResult.Success &&
                            deliveryMethodsResult is ObtainingDeliveryMethodsResult.Success
                    -> {
                        orderDetails = orderDetails.copy(
                            deliveryMethod = deliveryMethodsResult.data[0],
                            paymentMethod = paymentMethodResult.data[0]
                        )
                        MakeOrderScreenState.ScreenData(
                            total = total,
                            paymentMethods = paymentMethodResult.data,
                            deliveryMethods = deliveryMethodsResult.data,
                            isCheckoutButtonAvailable = shouldActivateCheckoutButton()
                        )
                    }

                    paymentMethodResult is ObtainingPaymentMethodsResult.NetworkError ||
                            deliveryMethodsResult is ObtainingDeliveryMethodsResult.NetworkError
                    -> {
                        MakeOrderScreenState.NetworkError
                    }

                    paymentMethodResult is ObtainingPaymentMethodsResult.OtherError ||
                            deliveryMethodsResult is ObtainingDeliveryMethodsResult.OtherError
                    -> {
                        MakeOrderScreenState.OtherError
                    }

                    else -> {
                        MakeOrderScreenState.OtherError
                    }
                }
            }
        }
    }

    private fun onDeliveryMethodSelected(deliveryMethod: DeliveryMethod) {
        if (deliveryMethod.id != orderDetails.deliveryMethod.id) {
            orderDetails =
                orderDetails.copy(deliveryMethod = deliveryMethod, deliveryAddress = null)
        }

        mutateSuccessScreenState()
    }

    private fun onDeliveryAddressUpdated(address: String) {
        orderDetails = orderDetails.copy(deliveryAddress = address)

        mutateSuccessScreenState()
    }

    private fun onPaymentMethodSelected(paymentMethod: PaymentMethod) {
        if (paymentMethod.id != orderDetails.paymentMethod.id) {
            orderDetails = orderDetails.copy(paymentMethod = paymentMethod)
        }
    }


    private fun onRefreshClicked() {
        viewModelScope.launch(dispatchersProvider.io) {
            _screenState.value = MakeOrderScreenState.Loading
            _screenState.update {
                val paymentMethodResult = obtainPaymentMethodsUseCase()
                val deliveryMethodsResult = obtainDeliveryMethodsUseCase()

                when {
                    paymentMethodResult is ObtainingPaymentMethodsResult.Success &&
                            deliveryMethodsResult is ObtainingDeliveryMethodsResult.Success
                    -> {
                        orderDetails = orderDetails.copy(
                            deliveryMethod = deliveryMethodsResult.data[0],
                            paymentMethod = paymentMethodResult.data[0]
                        )
                        MakeOrderScreenState.ScreenData(
                            total = total,
                            paymentMethods = paymentMethodResult.data,
                            deliveryMethods = deliveryMethodsResult.data,
                            isCheckoutButtonAvailable = shouldActivateCheckoutButton()
                        )
                    }

                    paymentMethodResult is ObtainingPaymentMethodsResult.NetworkError ||
                            deliveryMethodsResult is ObtainingDeliveryMethodsResult.NetworkError
                    -> {
                        MakeOrderScreenState.NetworkError
                    }

                    paymentMethodResult is ObtainingPaymentMethodsResult.OtherError ||
                            deliveryMethodsResult is ObtainingDeliveryMethodsResult.OtherError
                    -> {
                        MakeOrderScreenState.OtherError
                    }

                    else -> {
                        MakeOrderScreenState.OtherError
                    }
                }
            }
        }
    }

    private fun onBackButtonPressed() {
        viewModelScope.launch {
            _screenEffect.emit(MakeOrderScreenEffect.NavigateBack)
        }
    }

    private fun shouldActivateCheckoutButton(): Boolean =
        (!orderDetails.deliveryMethod.isDelivery) ||
                (orderDetails.deliveryMethod.isDelivery && orderDetails.deliveryAddress != null)

    private fun mutateSuccessScreenState() {
        if (screenState.value is MakeOrderScreenState.ScreenData) {
            _screenState.value = (screenState.value as MakeOrderScreenState.ScreenData).copy(isCheckoutButtonAvailable = shouldActivateCheckoutButton() )
        }
    }
}