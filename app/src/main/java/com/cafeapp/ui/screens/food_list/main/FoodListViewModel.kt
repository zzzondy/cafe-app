package com.cafeapp.ui.screens.food_list.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.cafeapp.core.providers.dispatchers.DispatchersProvider
import com.cafeapp.domain.cart.usecase.AddFoodToCartUseCase
import com.cafeapp.domain.food_list.usecase.GetPagedFoodListUseCase
import com.cafeapp.domain.models.Food
import com.cafeapp.ui.screens.food_list.main.states.FoodListEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    getPagedFoodListUseCase: GetPagedFoodListUseCase,
    private val addFoodToCartUseCase: AddFoodToCartUseCase,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {
    val food = getPagedFoodListUseCase().flowOn(dispatchersProvider.io).cachedIn(viewModelScope)

    fun onEvent(event: FoodListEvent) {
        when (event) {
            is FoodListEvent.AddFoodToCart -> addFoodToCart(event.food)
        }
    }

    private fun addFoodToCart(food: Food) {
        viewModelScope.launch(dispatchersProvider.io) {
            addFoodToCartUseCase(food)
        }
    }
}