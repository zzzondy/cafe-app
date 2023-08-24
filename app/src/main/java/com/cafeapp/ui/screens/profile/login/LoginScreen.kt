package com.cafeapp.ui.screens.profile.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cafeapp.R
import com.cafeapp.core.util.UIText
import com.cafeapp.core.util.collectAsEffect
import com.cafeapp.ui.common.ui_components.LoadingDialog
import com.cafeapp.ui.screens.profile.ProfileNavGraph
import com.cafeapp.ui.screens.profile.login.states.LoginScreenEffect
import com.cafeapp.ui.screens.profile.login.states.LoginScreenEvent
import com.cafeapp.ui.screens.profile.login.states.LoginScreenState
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

    var isLoadingDialogActive by remember { mutableStateOf(false) }

    loginViewModel.loginScreenEffect.collectAsEffect { effect ->
        when (effect) {
            LoginScreenEffect.NavigateBack -> navigator.popBackStack()

            LoginScreenEffect.ShowLoadingDialog -> isLoadingDialogActive = true

            LoginScreenEffect.HideLoadingDialog -> isLoadingDialogActive = false
        }
    }

    if (isLoadingDialogActive) {
        LoadingDialog()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = UIText.StringResource(R.string.sign_in).asString()) },
                navigationIcon = {
                    IconButton(onClick = { loginViewModel.onEvent(LoginScreenEvent.OnBackButtonClicked) }) {
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
                .padding(paddingValues)
        )
    }
}


@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
private fun LoginScreenPart(
    loginScreenState: LoginScreenState,
    onEvent: (event: LoginScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var email by rememberSaveable { mutableStateOf("") }

    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    val passwordTextFieldFocusRequester = FocusRequester()

    val focusManager = LocalFocusManager.current
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { focusManager.clearFocus() }
            }
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

        AnimatedVisibility(
            visible = loginScreenState is LoginScreenState.SomeError
        ) {
            Text(
                text = if (loginScreenState is LoginScreenState.SomeError) {
                    loginScreenState.message.asString()
                } else {
                    ""
                },
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            label = { Text(text = UIText.StringResource(R.string.email).asString()) },
            keyboardActions = KeyboardActions(onNext = { passwordTextFieldFocusRequester.requestFocus() }),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Email, contentDescription = stringResource(
                        id = R.string.email_image
                    )
                )
            },
            trailingIcon = {
                if (email.isNotEmpty()) {
                    IconButton(onClick = { email = "" }) {
                        Icon(
                            imageVector = Icons.Rounded.Clear,
                            contentDescription = stringResource(
                                id = R.string.clear_image
                            )
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            singleLine = true,
            visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            label = { Text(text = UIText.StringResource(R.string.password).asString()) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Lock, contentDescription = stringResource(
                        id = R.string.lock_image
                    )
                )
            },
            trailingIcon = {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    val visibilityIcon =
                        if (passwordHidden) R.drawable.ic_round_visibility_24 else R.drawable.ic_round_visibility_off_24
                    Icon(
                        painter = painterResource(id = visibilityIcon),
                        contentDescription = stringResource(
                            id = R.string.visibility_password_image
                        )
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .focusRequester(passwordTextFieldFocusRequester)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                onEvent(
                    LoginScreenEvent.SignIn(
                        email = email,
                        password = password
                    )
                )
                focusManager.clearFocus()
            },
            enabled = email.isNotEmpty() && password.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Text(text = UIText.StringResource(R.string.sign_in).asString())
        }
    }
}