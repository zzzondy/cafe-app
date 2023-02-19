package com.cafeapp.ui.screens.cart.states.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cafeapp.domain.models.Food
import com.cafeapp.ui.screens.cart.CartItem


@Composable
fun NotEmptyCartScreenState(
    items: List<Pair<Food, Int>>,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    onSelectItem: (Food) -> Unit,
    onIncrementItemsCount: (Long) -> Unit,
    onDecrementItemsCount: (Long) -> Unit,
    onDeleteFoodFromCart: (Food) -> Unit
) {
    LazyColumn(
        state = listState,
        modifier = modifier
    ) {
        items(items, key = { it.first.id }) { food ->
            CartItem(
                food = food.first,
                itemsCount = food.second,
                onSelectItem = { onSelectItem(food.first) },
                onIncrementItemsCount = { onIncrementItemsCount(food.first.id) },
                onDecrementItemsCount = { onDecrementItemsCount(food.first.id) },
                onDeleteFoodFromCart = { onDeleteFoodFromCart(food.first) },
            )
        }
    }
}