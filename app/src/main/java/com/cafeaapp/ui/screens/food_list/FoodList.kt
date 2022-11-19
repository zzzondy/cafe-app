package com.cafeaapp.ui.screens.food_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun FoodList(navigator: DestinationsNavigator) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Food list screen")
        Button(onClick = {
        }) {
            Text(text = "To details")
        }
    }
}