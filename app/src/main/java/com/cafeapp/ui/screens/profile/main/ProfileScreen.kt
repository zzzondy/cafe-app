package com.cafeapp.ui.screens.profile.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
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
import com.cafeapp.ui.screens.destinations.SettingsScreenDestination
import com.cafeapp.ui.screens.destinations.SignUpScreenDestination
import com.cafeapp.ui.screens.profile.main.states.UserAuthState
import com.cafeapp.ui.theme.CafeAppTheme
import com.cafeapp.ui.util.UiText
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val currentAuthState = profileViewModel.userAuthState.collectAsState().value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = UiText.StringResource(R.string.profile).asString()
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                        2.dp
                    )
                ),
                actions = {
                    IconButton(onClick = { navigator.navigate(SettingsScreenDestination) }) {
                        Icon(
                            imageVector = Icons.Rounded.Settings,
                            contentDescription = stringResource(
                                R.string.settings_image
                            )
                        )
                    }
                }
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
                    )
                    is UserAuthState.NotAuthenticated -> NotAuthenticatedStateScreen(onSignInClick = {
                        navigator.navigate(
                            LoginScreenDestination
                        )
                    }, onSignUpClick = { navigator.navigate(SignUpScreenDestination) })
                }
            }

            item {
                SettingsPartOfScreen()
            }
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
    user: User
) {
    var skeletonLoadingState by rememberSaveable { mutableStateOf(false) }

    ConstraintLayout(
        constraintSet = ConstraintSet {
            val userPicture = createRefFor(AuthenticatedStateScreenTags.userPicture)
            val userName = createRefFor(AuthenticatedStateScreenTags.userName)
            val userEmail = createRefFor(AuthenticatedStateScreenTags.userEmail)

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
        },
        modifier = Modifier.fillMaxWidth()
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(user.photoUrl)
                .error(R.drawable.ic_round_person_24)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(id = R.string.user_photo),
            modifier = Modifier
                .layoutId(AuthenticatedStateScreenTags.userPicture)
                .height(150.dp)
                .width(150.dp)
                .clip(CircleShape)
                .placeholder(
                    visible = skeletonLoadingState,
                    shape = CircleShape,
                    highlight = PlaceholderHighlight.shimmer(),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ),
            contentScale = ContentScale.Crop,
            onLoading = { skeletonLoadingState = true },
            onError = { skeletonLoadingState = false },
            onSuccess = { skeletonLoadingState = false },
        )

        Text(
            text = if (user.displayName == null || user.displayName?.isEmpty() == true) {
                UiText.StringResource(R.string.user_name).asString()
            } else {
                UiText.DynamicText(user.displayName!!).asString()
            },
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .placeholder(
                    visible = skeletonLoadingState,
                    highlight = PlaceholderHighlight.shimmer(),
                    color = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                    shape = ShapeDefaults.Small
                )
                .layoutId(AuthenticatedStateScreenTags.userName)
        )

        Text(
            text = UiText.DynamicText(user.email).asString(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .placeholder(
                    visible = skeletonLoadingState,
                    highlight = PlaceholderHighlight.shimmer(),
                    color = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                    shape = ShapeDefaults.Small
                )
                .layoutId(AuthenticatedStateScreenTags.userEmail)
        )
    }
}

private object AuthenticatedStateScreenTags {
    const val userPicture = "userPicture"
    const val userName = "userName"
    const val userEmail = "userEmail"
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
                "https://firebasestorage.googleapis.com/v0/b/cafe-app-project.appspot.com/o/qwetPLQG6wRrGK8vGJ2Jf47Ploz2.jpg?alt=media&token=02423417-746c-4c55-baa6-ffde6b8ecc59",
                "Artyom Rodionov"
            )
        )
    }
}

