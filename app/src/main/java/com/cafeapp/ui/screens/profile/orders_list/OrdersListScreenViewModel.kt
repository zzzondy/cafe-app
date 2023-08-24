package com.cafeapp.ui.screens.profile.orders_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeapp.core.providers.dispatchers.DispatchersProvider
import com.cafeapp.domain.orders.orders_list.states.ObtainingOrdersListResult
import com.cafeapp.domain.orders.orders_list.usecase.ObtainOrdersForUserUseCase
import com.cafeapp.ui.screens.profile.orders_list.states.OrdersListScreenEffect
import com.cafeapp.ui.screens.profile.orders_list.states.OrdersListScreenEvent
import com.cafeapp.ui.screens.profile.orders_list.states.OrdersListScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OrdersListScreenViewModel @Inject constructor(
    private val obtainOrdersForUserUseCase: ObtainOrdersForUserUseCase,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _screenState =
        MutableStateFlow<OrdersListScreenState>(OrdersListScreenState.Loading)
    val screenState = _screenState.asStateFlow()

    private val _screenEffect = MutableSharedFlow<OrdersListScreenEffect>()
    val screenEffect = _screenEffect.asSharedFlow()

    init {
        getUserOrders()
    }

    fun onEvent(event: OrdersListScreenEvent) {
        when (event) {
            OrdersListScreenEvent.OnRefresh -> onRefresh()

            OrdersListScreenEvent.OnBackButtonClicked -> onBackButtonClicked()
        }
    }

    private fun getUserOrders() {
        viewModelScope.launch(dispatchersProvider.io) {
            _screenState.update {
                when (val result = obtainOrdersForUserUseCase()) {
                    is ObtainingOrdersListResult.Success -> {
                        if (result.data.isNotEmpty()) {
                            OrdersListScreenState.Data(result.data)
                        } else {
                            OrdersListScreenState.EmptyOrdersList
                        }
                    }

                    is ObtainingOrdersListResult.NetworkError -> OrdersListScreenState.NetworkError

                    is ObtainingOrdersListResult.OtherError -> OrdersListScreenState.OtherError

                    is ObtainingOrdersListResult.UserIsNotAuthenticatedError -> OrdersListScreenState.OtherError
                }
            }
        }
    }

    private fun onRefresh() {
        viewModelScope.launch(dispatchersProvider.io) {
            _screenState.update { OrdersListScreenState.Loading }

            _screenState.update {
                when (val result = obtainOrdersForUserUseCase()) {
                    is ObtainingOrdersListResult.Success -> {
                        if (result.data.isNotEmpty()) {
                            OrdersListScreenState.Data(result.data)
                        } else {
                            OrdersListScreenState.EmptyOrdersList
                        }
                    }

                    is ObtainingOrdersListResult.NetworkError -> OrdersListScreenState.NetworkError

                    is ObtainingOrdersListResult.OtherError -> OrdersListScreenState.OtherError

                    is ObtainingOrdersListResult.UserIsNotAuthenticatedError -> OrdersListScreenState.OtherError
                }
            }
        }
    }

    private fun onBackButtonClicked() {
        viewModelScope.launch {
            _screenEffect.emit(OrdersListScreenEffect.NavigateBack)
        }
    }
}