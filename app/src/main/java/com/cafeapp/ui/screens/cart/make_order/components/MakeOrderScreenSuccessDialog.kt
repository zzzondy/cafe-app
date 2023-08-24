package com.cafeapp.ui.screens.cart.make_order.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.cafeapp.R
import com.cafeapp.core.util.UIText


@Composable
fun MakeOrderScreenSuccessDialog(onDismissRequest: () -> Unit = {}) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = UIText.StringResource(R.string.confirm).asString())
            }
        },
        title = {
            Text(text = UIText.StringResource(R.string.make_order_successful).asString())
        },
        text = {
            Text(text = UIText.StringResource(R.string.make_order_successful_details).asString())
        }
    )
}