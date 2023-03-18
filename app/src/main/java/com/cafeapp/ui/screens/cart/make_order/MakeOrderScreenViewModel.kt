package com.cafeapp.ui.screens.cart.make_order

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeapp.core.providers.dispatchers.DispatchersProvider
import com.cafeapp.domain.make_order.states.ObtainingDeliveryMethodsResult
import com.cafeapp.domain.make_order.states.ObtainingPaymentMethodsResult
import com.cafeapp.domain.make_order.use_case.ObtainDeliveryMethodsUseCase
import com.cafeapp.domain.make_order.use_case.ObtainPaymentMethodsUseCase
import com.cafeapp.ui.screens.cart.make_order.states.MakeOrderScreenState
import com.cafeapp.ui.screens.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MakeOrderScreenViewModel @Inject constructor(
    private val obtainPaymentMethodsUseCase: ObtainPaymentMethodsUseCase,
    private val obtainDeliveryMethodsUseCase: ObtainDeliveryMethodsUseCase,
    private val dispatchersProvider: DispatchersProvider,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val selectedFoodItems =
        savedStateHandle.navArgs<MakeOrderScreenNavArgs>().selectedFoodIds.toList()

    private val total = savedStateHandle.navArgs<MakeOrderScreenNavArgs>().total

    private val _screenState = MutableStateFlow<MakeOrderScreenState>(MakeOrderScreenState.Loading)
    val screenState = _screenState.asStateFlow()


    init {
        screenEntered()
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
                        MakeOrderScreenState.ScreenData(
                            total = total,
                            paymentMethods = paymentMethodResult.data,
                            deliveryMethods = deliveryMethodsResult.data
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
}