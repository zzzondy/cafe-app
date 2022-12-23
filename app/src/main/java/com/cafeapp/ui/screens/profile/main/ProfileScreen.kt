package com.cafeapp.ui.screens.profile.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cafeapp.R
import com.cafeapp.domain.models.User
import com.cafeapp.ui.screens.destinations.LoginScreenDestination
import com.cafeapp.ui.screens.destinations.SignUpScreenDestination
import com.cafeapp.ui.screens.profile.ProfileScreenTransitions
import com.cafeapp.ui.screens.profile.main.states.ProfileEvent
import com.cafeapp.ui.screens.profile.main.states.UserAuthState
import com.cafeapp.ui.screens.profile.states.LoadingState
import com.cafeapp.ui.theme.CafeAppTheme
import com.cafeapp.ui.util.UiText
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination(style = ProfileScreenTransitions::class)
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val currentAuthState = profileViewModel.userAuthState.collectAsState().value
    val loadingState by profileViewModel.loadingState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = UiText.StringResource(R.string.profile).asString()
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            when (currentAuthState) {
                is UserAuthState.Authenticated -> AuthenticatedStateScreen(
                    user = currentAuthState.user,
                    onEvent = profileViewModel::onEvent,
                    loadingState = loadingState
                )
                is UserAuthState.NotAuthenticated -> NotAuthenticatedStateScreen(onSignInClick = {
                    navigator.navigate(
                        LoginScreenDestination
                    )
                }, onSignUpClick = { navigator.navigate(SignUpScreenDestination) })
            }
            SettingsPartOfScreen()
        }
    }
}

@Composable
private fun SettingsPartOfScreen() {

}

@Composable
private fun NotAuthenticatedStateScreen(onSignInClick: () -> Unit, onSignUpClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = UiText.StringResource(R.string.profile_explanation).asString(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
        )

        Button(
            onClick = onSignInClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(text = UiText.StringResource(R.string.sign_in).asString())
        }

        OutlinedButton(
            onClick = onSignUpClick, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
        ) {
            Text(text = UiText.StringResource(R.string.sign_up).asString())
        }

    }
}

@Composable
private fun AuthenticatedStateScreen(
    user: User,
    onEvent: (ProfileEvent) -> Unit,
    loadingState: LoadingState
) {
    ConstraintLayout(
        constraintSet = ConstraintSet {
            val userPicture = createRefFor(AuthenticatedStateScreenTags.userPicture)
            val userName = createRefFor(AuthenticatedStateScreenTags.userName)
            val userEmail = createRefFor(AuthenticatedStateScreenTags.userEmail)
            val signOutButton = createRefFor(AuthenticatedStateScreenTags.signOutButton)
            val loading = createRefFor(AuthenticatedStateScreenTags.loading)

            constrain(userPicture) {
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
                top.linkTo(parent.top, 16.dp)
            }

            constrain(userName) {
                start.linkTo(userPicture.start)
                end.linkTo(userPicture.end)
                top.linkTo(userPicture.bottom, 8.dp)
            }

            constrain(userEmail) {
                start.linkTo(userName.start)
                end.linkTo(userName.end)
                top.linkTo(userName.bottom, 8.dp)
            }

            constrain(signOutButton) {
                start.linkTo(userEmail.start)
                end.linkTo(userEmail.end)
                top.linkTo(userEmail.bottom, 16.dp)
            }

            constrain(loading) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(user.photoUrl)
                .error(R.drawable.user_svgrepo_com)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(id = R.string.user_photo),
            modifier = Modifier
                .layoutId(AuthenticatedStateScreenTags.userPicture)
                .width(150.dp)
                .height(100.dp)
                .clip(CircleShape),
            placeholder = painterResource(id = R.drawable.user_svgrepo_com)
        )
        Text(
            text = if (user.displayName == null || user.displayName?.isEmpty() == true) {
                UiText.StringResource(R.string.user_name).asString()
            } else {
                UiText.DynamicText(user.displayName!!).asString()
            },
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.layoutId(AuthenticatedStateScreenTags.userName)
        )

        Text(
            text = UiText.DynamicText(user.email).asString(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.layoutId(AuthenticatedStateScreenTags.userEmail)
        )

        OutlinedButton(
            onClick = { onEvent(ProfileEvent.SignOutClicked) },
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.layoutId(AuthenticatedStateScreenTags.signOutButton)
        ) {
            Text(text = UiText.StringResource(R.string.sign_out).asString())
        }

        if (loadingState == LoadingState.Loading) {
            CircularProgressIndicator(modifier = Modifier.layoutId(AuthenticatedStateScreenTags.loading))
        }
    }
}

private object AuthenticatedStateScreenTags {
    const val userPicture = "userPicture"
    const val userName = "userName"
    const val userEmail = "userEmail"
    const val signOutButton = "signOutButton"
    const val loading = "loading"
}

@Preview
@Composable
fun NotAuthenticatedStateScreenPreview() {
    CafeAppTheme {
        NotAuthenticatedStateScreen(onSignInClick = {}, onSignUpClick = {})
    }
}

@Preview
@Composable
fun AuthenticatedStateScreenPreview() {
    CafeAppTheme {
        AuthenticatedStateScreen(
            user = User(
                "1",
                "artemr19032006@yandex.ru",
                null,
                "Artyom Rodionov"
            ), onEvent = {}, LoadingState.NotLoading
        )
    }
}

