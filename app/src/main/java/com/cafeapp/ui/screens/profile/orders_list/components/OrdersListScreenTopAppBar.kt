package com.cafeapp.ui.screens.profile.orders_list.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.cafeapp.R
import com.cafeapp.core.util.UIText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersListScreenTopAppBar(
    onBackButtonClicked: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(text = UIText.StringResource(R.string.orders_list).asString())
        },
        navigationIcon = {
            IconButton(onClick = onBackButtonClicked) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.arrow_back_image)
                )
            }
        }
    )
}