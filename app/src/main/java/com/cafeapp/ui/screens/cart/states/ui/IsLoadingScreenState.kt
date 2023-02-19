package com.cafeapp.ui.screens.cart.states.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cafeapp.ui.screens.cart.CartItem
import com.cafeapp.ui.theme.CafeAppTheme
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.placeholder

@Composable
fun IsLoadingScreenState(modifier: Modifier = Modifier) {
    LazyColumn(userScrollEnabled = false, modifier = modifier) {
        items(IsLoadingScreenStateConfig.PLACEHOLDER_ITEMS_COUNT, key = { it }) {
            CartItem(
                food = null,
                modifier = Modifier
                    .placeholder(
                        visible = true,
                        color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                        highlight = PlaceholderHighlight.fade(),
                        shape = MaterialTheme.shapes.medium
                    )
            )
        }
    }
}

private object IsLoadingScreenStateConfig {
    const val PLACEHOLDER_ITEMS_COUNT = 5
}

@Preview
@Composable
fun IsLoadingScreenStatePreview() {
    CafeAppTheme(darkTheme = true) {
        IsLoadingScreenState(modifier = Modifier.fillMaxSize())
    }
}