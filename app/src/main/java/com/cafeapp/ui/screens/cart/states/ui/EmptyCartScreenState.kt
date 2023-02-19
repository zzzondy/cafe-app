package com.cafeapp.ui.screens.cart.states.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cafeapp.R
import com.cafeapp.core.util.UiText
import com.cafeapp.ui.theme.CafeAppTheme

@Composable
fun EmptyCartScreenState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.ShoppingCart,
            contentDescription = stringResource(R.string.cart),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(60.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = UiText.StringResource(R.string.your_cart_is_empty).asString(),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = UiText.StringResource(R.string.add_something_to_cart).asString(),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
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