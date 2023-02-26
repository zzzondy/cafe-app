package com.cafeapp.ui.screens.cart.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.cafeapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreenTopAppBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onDeleteSelected: () -> Unit = {},
) {
    TopAppBar(
        title = { Text(text = title) },
        scrollBehavior = scrollBehavior,
        actions = {
            IconButton(onClick = onDeleteSelected) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = stringResource(R.string.delete_selected)
                )
            }
        }
    )
}