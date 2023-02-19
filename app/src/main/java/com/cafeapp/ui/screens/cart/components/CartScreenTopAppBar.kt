package com.cafeapp.ui.screens.cart.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.cafeapp.R
import com.cafeapp.core.util.UiText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreenTopAppBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onDeleteSelected: () -> Unit = {},
) {
    var menuExpanded by rememberSaveable { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = title) },
        scrollBehavior = scrollBehavior,
        actions = {
            IconButton(onClick = {
                menuExpanded = !menuExpanded
            }
            ) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = stringResource(R.string.menu)
                )
            }

            CartScreenMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
                onDeleteSelected = {
                    onDeleteSelected()
                    menuExpanded = false
                }
            )
        }
    )
}

@Composable
private fun CartScreenMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit = {},
    onDeleteSelected: () -> Unit = {},
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = UiText.StringResource(R.string.delete_selected).asString(),
                    color = MaterialTheme.colorScheme.error
                )
            },
            onClick = onDeleteSelected,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = stringResource(R.string.delete_selected),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        )
    }
}