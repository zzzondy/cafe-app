package com.cafeapp.ui.screens.profile.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cafeapp.R
import com.cafeapp.ui.common.LoadingDialog
import com.cafeapp.ui.screens.profile.login.states.LoginScreenEvent
import com.cafeapp.ui.screens.profile.login.states.LoginScreenState
import com.cafeapp.ui.common.states.LoadingState
import com.cafeapp.ui.theme.CafeAppTheme
import com.cafeapp.core.util.UiText
import com.cafeapp.ui.screens.profile.ProfileNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@ProfileNavGraph
@Destination(style = LoginScreenTransitions::class)
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val loginScreenState by loginViewModel.loginScreenState.collectAsState()
    val loadingState by loginViewModel.loadingState.collectAsState()

    LaunchedEffect(key1 = loginScreenState) {
        if (loginScreenState is LoginScreenState.SuccessfullySignIn) {
            navigator.popBackStack()
        }
    }

    if (loadingState == LoadingState.Loading) {
        LoadingDialog()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = UiText.StringResource(R.string.sign_in).asString()) },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = stringResource(R.string.arrow_back_image)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LoginScreenPart(
            loginScreenState = loginScreenState,
            onEvent = loginViewModel::onEvent,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(paddingValues)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginScreenPart(
    loginScreenState: LoginScreenState,
    onEvent: (event: LoginScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val email = rememberSaveable { mutableStateOf("") }

    val password = rememberSaveable { mutableStateOf("") }
    val passwordHidden = rememberSaveable { mutableStateOf(true) }
    val passwordTextFieldFocusRequester = FocusRequester()

    val isError = loginScreenState is LoginScreenState.WrongCredentialsError

    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures { focusManager.clearFocus() }
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_app_logo),
            contentDescription = stringResource(
                R.string.app_logo_image
            ),
            modifier = Modifier
                .size(192.dp)
                .padding(bottom = 64.dp)
                .scale(1f),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )

        AnimatedVisibility(visible = loginScreenState is LoginScreenState.NetworkUnavailable) {
            Text(
                text = UiText.StringResource(R.string.network_unavailable).asString(),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            )
        }

        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            label = { Text(text = UiText.StringResource(R.string.email).asString()) },
            keyboardActions = KeyboardActions(onNext = { passwordTextFieldFocusRequester.requestFocus() }),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Email, contentDescription = stringResource(
                        id = R.string.email_image
                    )
                )
            },
            trailingIcon = {
                if (email.value.isNotEmpty()) {
                    IconButton(onClick = { email.value = "" }) {
                        Icon(
                            imageVector = Icons.Rounded.Clear,
                            contentDescription = stringResource(
                                id = R.string.clear_image
                            )
                        )
                    }
                }
            },
            supportingText = {
                if (isError) {
                    Text(
                        text = UiText.StringResource(R.string.sign_in_error).asString(),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            isError = isError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
        )

        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
            },
            singleLine = true,
            visualTransformation = if (passwordHidden.value) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            label = { Text(text = UiText.StringResource(R.string.password).asString()) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Lock, contentDescription = stringResource(
                        id = R.string.lock_image
                    )
                )
            },
            trailingIcon = {
                IconButton(onClick = { passwordHidden.value = !passwordHidden.value }) {
                    val visibilityIcon =
                        if (passwordHidden.value) R.drawable.ic_round_visibility_24 else R.drawable.ic_round_visibility_off_24
                    Icon(
                        painter = painterResource(id = visibilityIcon),
                        contentDescription = stringResource(
                            id = R.string.visibility_password_image
                        )
                    )
                }
            },
            supportingText = {
                if (isError) {
                    Text(
                        text = UiText.StringResource(R.string.sign_in_error).asString(),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            isError = isError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .focusRequester(passwordTextFieldFocusRequester),
        )

        Button(
            onClick = {
                onEvent(
                    LoginScreenEvent.SignIn(
                        email = email.value,
                        password = password.value
                    )
                )
                focusManager.clearFocus()
            },
            enabled = email.value.isNotEmpty() && password.value.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
            Text(text = UiText.StringResource(R.string.sign_in).asString())
        }
    }
}


@Preview
@Composable
fun LoginScreenPreview() {
    CafeAppTheme {
//        LoginScreen()
    }
}