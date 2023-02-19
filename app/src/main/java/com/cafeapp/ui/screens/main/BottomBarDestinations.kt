package com.cafeapp.ui.screens.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.cafeapp.R
import com.cafeapp.ui.screens.NavGraphs
import com.ramcosta.composedestinations.spec.NavGraphSpec

enum class BottomBarDestinations(
    val graph: NavGraphSpec,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    FoodListScreen(
        graph = NavGraphs.foodList,
        icon = Icons.Rounded.Home,
        R.string.food_list
    ),
    CartScreen(
        graph = NavGraphs.cart,
        icon = Icons.Rounded.ShoppingCart,
        R.string.cart
    ),
    ProfileScreen(
        graph = NavGraphs.profile,
        icon = Icons.Rounded.Person,
        R.string.profile
    ),
}