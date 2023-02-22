package com.cafeapp.ui.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeapp.core.providers.dispatchers.DispatchersProvider
import com.cafeapp.domain.cart.states.CartTransactionsResult
import com.cafeapp.domain.cart.states.IncrementResult
import com.cafeapp.domain.cart.states.ObtainingCartResult
import com.cafeapp.domain.cart.usecase.*
import com.cafeapp.domain.models.Food
import com.cafeapp.ui.screens.cart.states.CartScreenEffect
import com.cafeapp.ui.screens.cart.states.CartScreenEvent
import com.cafeapp.ui.screens.cart.states.CartScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    private val obtainUserCartUseCase: ObtainUserCartUseCase,
    private val calculateTotalUseCase: CalculateTotalUseCase,
    private val incrementItemsCountUseCase: IncrementItemsCountUseCase,
    private val decrementItemsCountUseCase: DecrementItemsCountUseCase,
    private val deleteFoodFromCartUseCase: DeleteFoodFromCartUseCase,
    private val deleteSelectedFoodUseCase: DeleteSelectedFoodUseCase,
    private val dispatchersProvider: DispatchersProvider
) :
    ViewModel() {

    private val _cartScreenState = MutableStateFlow<CartScreenState>(CartScreenState.IsLoading)
    val cartScreenState = _cartScreenState.asStateFlow()

    private val _cartScreenEffect = MutableSharedFlow<CartScreenEffect>()
    val cartScreenEffect = _cartScreenEffect.asSharedFlow()

    private val currentFoodList = mutableListOf<Pair<Food, Int>>()

    private val selectedFoods = mutableListOf<Pair<Food, Int>>()

    private val _currentTotal = MutableStateFlow(0)
    val currentTotal = _currentTotal.asStateFlow()


    fun onEvent(event: CartScreenEvent) {
        when (event) {
            is CartScreenEvent.ScreenEntered -> {
                getUserCart()
            }

            is CartScreenEvent.MakeOrderClicked -> {
                makeOrder()
            }

            is CartScreenEvent.ItemSelected -> {
                itemSelected(event.food)
            }

            is CartScreenEvent.DecrementItemsCount -> {
                decrementItemsCount(event.foodId)
            }

            is CartScreenEvent.IncrementItemsCount -> {
                incrementItemsCount(event.foodId)
            }

            is CartScreenEvent.DeleteSelected -> {
                deleteSelectedFood()
            }

            is CartScreenEvent.DeleteFoodFromCart -> {
                deleteFoodFromCart(event.food)
            }

            is CartScreenEvent.OnRefresh -> {
                getUserCart()
            }
        }
    }

    private fun makeOrder() {

    }

    private fun itemSelected(food: Food) {
        val foodIndex = currentFoodList.indexOfFirst { it.first == food }
        if (selectedFoods.find { it.first == currentFoodList[foodIndex].first } != null) {
            selectedFoods.remove(currentFoodList[foodIndex])
            _currentTotal.update {
                calculateTotalUseCase(selectedFoods)
            }
        } else {
            selectedFoods.add(currentFoodList[foodIndex])
            _currentTotal.update {
                calculateTotalUseCase(selectedFoods)
            }
        }
    }

    private fun updateSelectedFoods(food: Food, itemsCount: Int) {
        val index = selectedFoods.indexOfFirst { it.first == food }
        if (index != -1) {
            selectedFoods[index] = food to itemsCount
            _currentTotal.update {
                calculateTotalUseCase(selectedFoods)
            }
        }
    }

    private fun incrementItemsCount(foodId: Long) {
        viewModelScope.launch(dispatchersProvider.io) {
            when (val result = incrementItemsCountUseCase(foodId)) {
                is IncrementResult.Success -> {
                    val index = currentFoodList.indexOfFirst { it.first.id == foodId }
                    currentFoodList[index] = currentFoodList[index].first to result.count
                    updateSelectedFoods(currentFoodList[index].first, result.count)
                    _cartScreenState.update { CartScreenState.FoodList(currentFoodList.toImmutableList()) }
                }

                is IncrementResult.NetworkError -> {
                    _cartScreenEffect.emit(CartScreenEffect.ShowNetworkErrorSnackBar)
                }

                is IncrementResult.OtherError -> {
                    _cartScreenEffect.emit(CartScreenEffect.ShowNetworkErrorSnackBar)
                }
            }
        }
    }

    private fun decrementItemsCount(foodId: Long) {
        viewModelScope.launch(dispatchersProvider.io) {
            val index = currentFoodList.indexOfFirst { it.first.id == foodId }
            when (val result = decrementItemsCountUseCase(foodId, currentFoodList[index].second)) {
                is IncrementResult.Success -> {
                    currentFoodList[index] = currentFoodList[index].first to result.count
                    updateSelectedFoods(currentFoodList[index].first, result.count)
                    _cartScreenState.update { CartScreenState.FoodList(currentFoodList.toImmutableList()) }
                }

                is IncrementResult.NetworkError -> {
                    _cartScreenEffect.emit(CartScreenEffect.ShowNetworkErrorSnackBar)
                }

                is IncrementResult.OtherError -> {
                    _cartScreenEffect.emit(CartScreenEffect.ShowNetworkErrorSnackBar)
                }
            }
        }
    }

    private fun getUserCart() {
        viewModelScope.launch(dispatchersProvider.io) {
            _cartScreenState.update {
                when (val obtainingCartResult = obtainUserCartUseCase()) {
                    is ObtainingCartResult.Success -> {
                        if (obtainingCartResult.foodList.isNotEmpty()) {
                            currentFoodList.clear()
                            currentFoodList.addAll(obtainingCartResult.foodList)
                            CartScreenState.FoodList(obtainingCartResult.foodList)
                        } else {
                            CartScreenState.EmptyFoodList
                        }
                    }

                    is ObtainingCartResult.NetworkError -> {
                        CartScreenState.NetworkError
                    }

                    is ObtainingCartResult.OtherError -> {
                        CartScreenState.OtherError
                    }
                }
            }
        }
    }

    private fun deleteFoodFromCart(food: Food) {
        viewModelScope.launch(dispatchersProvider.io) {
            val foodIndex = currentFoodList.indexOfFirst { it.first.id == food.id }
            when (deleteFoodFromCartUseCase(food)) {
                is CartTransactionsResult.Success -> {
                    val index = selectedFoods.indexOfFirst { it.first.id == food.id }
                    if (index != -1) {
                        selectedFoods.removeAt(index)
                        _currentTotal.update {
                            calculateTotalUseCase(selectedFoods)
                        }
                    }

                    currentFoodList.removeAt(foodIndex)
                    _cartScreenState.update {
                        if (currentFoodList.isEmpty()) {
                            CartScreenState.EmptyFoodList
                        } else {
                            CartScreenState.FoodList(currentFoodList.toImmutableList())
                        }
                    }
                }

                is CartTransactionsResult.NetworkError -> {
                    _cartScreenEffect.emit(CartScreenEffect.ShowNetworkErrorSnackBar)
                }

                is CartTransactionsResult.OtherError -> {
                    _cartScreenEffect.emit(CartScreenEffect.ShowNetworkErrorSnackBar)
                }
            }
        }
    }

    private fun deleteSelectedFood() {
        viewModelScope.launch(dispatchersProvider.io) {
            when (deleteSelectedFoodUseCase(selectedFoods)) {
                is CartTransactionsResult.Success -> {
                    selectedFoods.forEach { pair ->
                        val foodIndex =
                            currentFoodList.indexOfFirst { it.first.id == pair.first.id }
                        currentFoodList.removeAt(foodIndex)
                    }

                    selectedFoods.clear()
                    _currentTotal.update { calculateTotalUseCase(selectedFoods) }
                    _cartScreenState.update {
                        if (currentFoodList.isEmpty()) {
                            CartScreenState.EmptyFoodList
                        } else {
                            CartScreenState.FoodList(currentFoodList.toImmutableList())
                        }
                    }
                }

                is CartTransactionsResult.NetworkError -> {
                    _cartScreenEffect.emit(CartScreenEffect.ShowNetworkErrorSnackBar)
                }

                is CartTransactionsResult.OtherError -> {
                    _cartScreenEffect.emit(CartScreenEffect.ShowNetworkErrorSnackBar)
                }
            }
        }
    }
}