package com.cafeapp.ui.screens.profile.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cafeapp.R
import com.cafeapp.core.util.UIText
import com.cafeapp.core.util.collectAsEffect
import com.cafeapp.ui.screens.destinations.LoginScreenDestination
import com.cafeapp.ui.screens.destinations.OrdersListScreenDestination
import com.cafeapp.ui.screens.destinations.SettingsScreenDestination
import com.cafeapp.ui.screens.destinations.SignUpScreenDestination
import com.cafeapp.ui.screens.profile.ProfileNavGraph
import com.cafeapp.ui.screens.profile.main.states.ProfileScreenEffect
import com.cafeapp.ui.screens.profile.main.states.ProfileScreenEvent
import com.cafeapp.ui.screens.profile.main.states.UserAuthState
import com.cafeapp.ui.screens.profile.main.states.ui.AuthenticatedStateScreen
import com.cafeapp.ui.screens.profile.main.states.ui.NotAuthenticatedStateScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@ProfileNavGraph(start = true)
@Destination(style = ProfileScreenTransitions::class)
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val currentAuthState = profileViewModel.userAuthState.collectAsState().value
    profileViewModel.screenEffect.collectAsEffect { effect ->
        when (effect) {
            ProfileScreenEffect.NavigateToSettings -> navigator.navigate(SettingsScreenDestination)

            ProfileScreenEffect.NavigateToOrdersList -> navigator.navigate(OrdersListScreenDestination)
        }
    }

    Scaffold(
        topBar = {
            ProfileTopAppBar(
                title = UIText.StringResource(R.string.profile).asString(),
                onSettingsClicked = { profileViewModel.onEvent(ProfileScreenEvent.OnSettingClicked) }
            )
        },
        modifier = Modifier.padding(bottom = 80.dp)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            item {
                when (currentAuthState) {
                    is UserAuthState.Authenticated -> AuthenticatedStateScreen(
                        user = currentAuthState.user,
                        onOrdersListClicked = {
                            profileViewModel.onEvent(ProfileScreenEvent.OnOrdersListClicked)
                        }
                    )
                    is UserAuthState.NotAuthenticated -> NotAuthenticatedStateScreen(onSignInClick = {
                        navigator.navigate(
                            LoginScreenDestination
                        )
                    }, onSignUpClick = { navigator.navigate(SignUpScreenDestination) })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileTopAppBar(
    title: String,
    onSettingsClicked: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                2.dp
            )
        ),
        actions = {
            IconButton(onClick = onSettingsClicked::invoke) {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = stringResource(
                        R.string.settings_image
                    )
                )
            }
        }
    )
}

