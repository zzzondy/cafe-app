package com.cafeapp.ui.screens.cart.states.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cafeapp.R
import com.cafeapp.core.util.UiText
import com.cafeapp.ui.theme.CafeAppTheme

@Composable
fun EmptyCartScreenState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = UiText.StringResource(R.string.your_cart_is_empty).asString(),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Preview
@Composable
private fun EmptyCartScreenStatePreview() {
    CafeAppTheme(darkTheme = true) {
        EmptyCartScreenState()
    }
}