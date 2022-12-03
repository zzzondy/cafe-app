package com.cafeapp.ui.screens.food_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.cafeapp.core.providers.dispatchers.DispatchersProvider
import com.cafeapp.domain.food_list.usecase.GetPagedFoodListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    getPagedFoodListUseCase: GetPagedFoodListUseCase,
    dispatchersProvider: DispatchersProvider
) : ViewModel() {
    val food = getPagedFoodListUseCase().flowOn(dispatchersProvider.io).cachedIn(viewModelScope)
}