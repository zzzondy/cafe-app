package com.cafeapp.ui.screens.profile.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cafeapp.R
import com.cafeapp.ui.screens.profile.login.states.LoadingState
import com.cafeapp.ui.screens.profile.login.states.LoginScreenEvent
import com.cafeapp.ui.screens.profile.login.states.LoginScreenState
import com.cafeapp.ui.theme.CafeAppTheme
import com.cafeapp.ui.util.UiText
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun LoginScreen(loginViewModel: LoginViewModel = hiltViewModel()) {
    val email = rememberSaveable { mutableStateOf("") }

    val password = rememberSaveable { mutableStateOf("") }
    val passwordHidden = rememberSaveable { mutableStateOf(true) }
    val passwordTextFieldFocusRequester = FocusRequester()

    val loadingState = loginViewModel.loadingState.collectAsState()
    val loginScreenState = loginViewModel.loginScreenState.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (loadingState.value == LoadingState.Loading)
            CircularProgressIndicator()

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = email.value,
                onValueChange = {
                    email.value = it
                    loginViewModel.onEvent(LoginScreenEvent.EmailFieldChanged(it))
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
            )

            OutlinedTextField(
                value = password.value,
                onValueChange = {
                    password.value = it
                    loginViewModel.onEvent(LoginScreenEvent.PasswordFieldChanged(it))
                },
                singleLine = true,
                visualTransformation = if (passwordHidden.value) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
                    .focusRequester(passwordTextFieldFocusRequester),
            )

            Button(
                onClick = {
                    loginViewModel.onEvent(
                        LoginScreenEvent.SignIn(
                            email = email.value,
                            password = password.value
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {
                Text(text = UiText.StringResource(R.string.sign_in).asString())
            }

            OutlinedButton(
                onClick = { /*TODO*/ }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            ) {
                Text(text = UiText.StringResource(R.string.sign_up).asString())
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    CafeAppTheme {
        LoginScreen()
    }
}