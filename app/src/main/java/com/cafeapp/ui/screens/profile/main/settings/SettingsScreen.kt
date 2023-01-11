package com.cafeapp.ui.screens.profile.main.settings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cafeapp.R
import com.cafeapp.ui.screens.profile.main.settings.states.SettingsScreenEvent
import com.cafeapp.ui.screens.profile.main.settings.states.SettingsScreenState
import com.cafeapp.ui.util.UiText
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination(style = SettingsScreenTransitions::class)
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator,
    settingsScreenViewModel: SettingsScreenViewModel = hiltViewModel()
) {

    var openSignOutDialog by rememberSaveable { mutableStateOf(false) }
    val uiState by settingsScreenViewModel.settingsScreenState.collectAsState()

    if (openSignOutDialog) {
        SignOutDialog(onDismissClicked = { openSignOutDialog = false }, onConfirmClicked = {
            settingsScreenViewModel.onEvent(SettingsScreenEvent.SignOutClicked)
            openSignOutDialog = false
            navigator.popBackStack()
        })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = UiText.StringResource(R.string.settings).asString()) },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = stringResource(
                                R.string.arrow_back_image
                            )
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (uiState) {
                is SettingsScreenState.UserAuthenticated -> {
                    accountSection(onSignOutClicked = { openSignOutDialog = true })
                    settingsSection(onAboutAppClicked = {})
                }

                is SettingsScreenState.UserNotAuthenticated -> {
                    settingsSection(onAboutAppClicked = {})
                }
            }
        }
    }
}

@Composable
private fun SignOutDialog(onDismissClicked: () -> Unit, onConfirmClicked: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismissClicked() },
        title = {
            Text(
                text = UiText.StringResource(R.string.are_you_sure_you_want_to_exit).asString()
            )
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirmClicked()
            }) {
                Text(text = UiText.StringResource(R.string.sign_out).asString())
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissClicked() }) {
                Text(text = UiText.StringResource(R.string.cancel).asString())
            }
        }
    )
}


@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.accountSection(onSignOutClicked: () -> Unit) {
    stickyHeader {
        Text(
            text = UiText.StringResource(R.string.account).asString(),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
        )
    }

    item {
        SettingsListItem(
            text = UiText.StringResource(R.string.sign_out).asString(),
            onClicked = { onSignOutClicked() },
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
            finishedElement = true
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.settingsSection(onAboutAppClicked: () -> Unit) {
    stickyHeader {
        Text(
            text = UiText.StringResource(R.string.about_app).asString(),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
        )
    }

    item {
        SettingsListItem(
            text = UiText.StringResource(R.string.about_app).asString(),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
            onClicked = { onAboutAppClicked() },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsListItem(
    text: String,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
    finishedElement: Boolean = false
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .clip(ShapeDefaults.Medium)
        .clickable { onClicked() })
    {
        ListItem(
            headlineText = {
                Text(
                    text = text
                )
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        if (!finishedElement) {
            Divider()
        }
    }
}
