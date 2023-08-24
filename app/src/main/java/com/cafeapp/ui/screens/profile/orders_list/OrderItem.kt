package com.cafeapp.ui.screens.profile.orders_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.cafeapp.R
import com.cafeapp.core.util.UIText
import com.cafeapp.domain.orders.orders_list.models.Order

@Composable
fun OrderItem(order: Order, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        ListItem(
            headlineText = {
                Text(
                    text = order.foods.joinToString { it.first.name },
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            },

            supportingText = {
                Text(text = UIText.StringResource(R.string.rubles, order.total).asString())
            },

            leadingContent = {
                Icon(
                    imageVector = Icons.Rounded.ShoppingCart,
                    contentDescription = stringResource(R.string.cart_desc)
                )
            },
        )

        Spacer(modifier = Modifier.height(8.dp))

        Divider(
            modifier = Modifier.fillMaxWidth()
        )
    }
}