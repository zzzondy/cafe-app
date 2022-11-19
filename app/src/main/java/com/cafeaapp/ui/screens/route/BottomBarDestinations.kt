package com.cafeaapp.ui.screens.route

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.cafeaapp.R
import com.cafeaapp.ui.screens.destinations.FoodListDestination
import com.cafeaapp.ui.screens.destinations.ProfileScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class BottomBarDestinations(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    FoodList(direction = FoodListDestination, icon = Icons.Rounded.List, R.string.food_list),
    ProfileScreen(direction = ProfileScreenDestination, icon = Icons.Rounded.Person, R.string.profile),
}