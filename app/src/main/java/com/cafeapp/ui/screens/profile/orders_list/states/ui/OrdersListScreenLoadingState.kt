package com.cafeapp.ui.screens.profile.orders_list.states.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cafeapp.R
import com.cafeapp.ui.theme.CafeAppTheme
import com.google.accompanist.placeholder.PlaceholderDefaults
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.material.shimmerHighlightColor
import com.google.accompanist.placeholder.placeholder

@Composable
fun OrdersListScreenLoadingState(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
    ) {
        items(OrdersListScreenLoadingStateConfig.PLACEHOLDERS_COUNT) {
            PlaceholderOrderItem(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun PlaceholderOrderItem(modifier: Modifier = Modifier) {
    Column {
        ListItem(
            headlineText = {
                Text(
                    text = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .placeholder(
                            visible = true,
                            color = PlaceholderDefaults.shimmerHighlightColor(
                                backgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                                    3.dp
                                )
                            ),
                            highlight = PlaceholderHighlight.shimmer(),
                            shape = MaterialTheme.shapes.extraLarge
                        )
                )
            },
            supportingText = {
                Text(
                    text = "",
                    modifier = Modifier
                        .fillMaxWidth(0.25f)
                        .placeholder(
                            visible = true,
                            color = PlaceholderDefaults.shimmerHighlightColor(
                                backgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                                    3.dp
                                )
                            ),
                            highlight = PlaceholderHighlight.shimmer(),
                            shape = MaterialTheme.shapes.extraLarge
                        )
                )
            },
            leadingContent = {
                Image(
                    imageVector = Icons.Rounded.ShoppingCart,
                    contentDescription = stringResource(R.string.cart_desc),
                    modifier = Modifier
                        .placeholder(
                            visible = true,
                            color = PlaceholderDefaults.shimmerHighlightColor(
                                backgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                                    3.dp
                                )
                            ),
                            highlight = PlaceholderHighlight.shimmer(),
                            shape = MaterialTheme.shapes.extraLarge
                        )
                )
            }
        )

        Spacer(modifier = Modifier.height(4.dp))

        Divider(
            modifier = Modifier.fillMaxWidth()
        )
    }
}


private object OrdersListScreenLoadingStateConfig {
    const val PLACEHOLDERS_COUNT = 5
}


@Preview
@Composable
fun OrdersListScreenLoadingStatePreview() {
    CafeAppTheme {
        OrdersListScreenLoadingState()
    }

}