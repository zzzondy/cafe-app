package com.cafeapp.ui.screens.food_list

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@NavGraph
annotation class FoodListNavGraph(
    val start: Boolean = false
)
