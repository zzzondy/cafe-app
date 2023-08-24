package com.cafeapp.ui.screens.cart.make_order.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.cafeapp.R
import com.cafeapp.core.util.UIText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakeOrderScreenTopAppBar(onBackPressed: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = UIText.StringResource(R.string.make_order).asString())
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.arrow_back_image)
                )
            }
        }
    )
}