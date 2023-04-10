package com.cafeapp.ui.screens.profile.orders_list.states.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cafeapp.R
import com.cafeapp.core.util.UIText

@Composable
fun OrdersListScreenEmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            imageVector = Icons.Rounded.ShoppingCart,
            contentDescription = stringResource(R.string.cart_desc),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
            modifier = Modifier.size(96.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = UIText.StringResource(R.string.empty_orders_list).asString(),
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = UIText.StringResource(R.string.empty_orders_list_details).asString(),
            style = MaterialTheme.typography.titleMedium
        )
    }
}