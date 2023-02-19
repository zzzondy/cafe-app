package com.cafeapp.ui.screens.profile.signUp_flow.signUp

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
import com.cafeapp.domain.auth.states.validation.ValidationEmailResult
import com.cafeapp.domain.auth.states.validation.ValidationPasswordResult
import com.cafeapp.ui.common.LoadingDialog
import com.cafeapp.ui.screens.destinations.UserDataScreenDestination
import com.cafeapp.ui.screens.profile.signUp_flow.SignUpSharedViewModel
import com.cafeapp.ui.screens.profile.signUp_flow.signUp.states.SignUpScreenEvent
import com.cafeapp.ui.screens.profile.signUp_flow.signUp.states.SignUpScreenState
import com.cafeapp.ui.common.states.LoadingState
import com.cafeapp.ui.theme.CafeAppTheme
import com.cafeapp.core.util.UiText
import com.cafeapp.ui.screens.profile.signUp_flow.SignUpFlowNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@SignUpFlowNavGraph(start = true)
@Destination(style = SignUpScreenTransitions::class)
@Composable
fun SignUpScreen(
    navigator: DestinationsNavigator,
    signUpSharedViewModel: SignUpSharedViewModel,
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {
    val signUpScreenState by signUpViewModel.signUpScreenState.collectAsState()
    val shouldNavigateToUserDataScreen by signUpViewModel.shouldNavigateToUserDataScreen.collectAsState()

    LaunchedEffect(key1 = signUpScreenState, key2 = shouldNavigateToUserDataScreen) {
        if (signUpScreenState is SignUpScreenState.Success && shouldNavigateToUserDataScreen) {
            navigator.navigate(UserDataScreenDestination)
            signUpViewModel.onEvent(SignUpScreenEvent.NavigateToUserDataScreen)
            signUpSharedViewModel.updateEmailAndPassword(
                (signUpScreenState as SignUpScreenState.Success).email,
                (signUpScreenState as SignUpScreenState.Success).password
            )
        }
    }

    val emailValidationState by signUpViewModel.validationEmailState.collectAsState()
    val passwordValidationState by signUpViewModel.validationPasswordState.collectAsState()

    val loadingState by signUpViewModel.loadingState.collectAsState()

    if (loadingState == LoadingState.Loading) {
        LoadingDialog()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = UiText.StringResource(R.string.sign_up).asString()) },
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
        SignUpScreenPart(
            onEvent = signUpViewModel::onEvent,
            emailValidationState = emailValidationState,
            passwordValidationState = passwordValidationState,
            signUpScreenState = signUpScreenState,
            modifier = Modifier
                .padding(paddingValues)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignUpScreenPart(
    onEvent: (SignUpScreenEvent) -> Unit,
    emailValidationState: ValidationEmailResult,
    passwordValidationState: ValidationPasswordResult,
    signUpScreenState: SignUpScreenState,
    modifier: Modifier = Modifier
) {
    val email = rememberSaveable { mutableStateOf("") }

    val password = rememberSaveable { mutableStateOf("") }
    val passwordTextFieldFocusRequester = FocusRequester()
    val passwordHidden = rememberSaveable { mutableStateOf(true) }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { focusManager.clearFocus() }
            }
            .verticalScroll(rememberScrollState())
            .imePadding(),
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

        AnimatedVisibility(
            visible = signUpScreenState is SignUpScreenState.NetworkUnavailableError ||
                    signUpScreenState is SignUpScreenState.UserAlreadyExistsError
        ) {
            if (signUpScreenState is SignUpScreenState.NetworkUnavailableError) {
                Text(
                    text = UiText.StringResource(R.string.network_unavailable).asString(),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                )
            } else {
                Text(
                    text = UiText.StringResource(R.string.user_already_exists).asString(),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                )
            }
        }

        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
                onEvent(SignUpScreenEvent.EmailChanged(it))
            },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Email, contentDescription = stringResource(
                        R.string.email_image
                    )
                )
            },
            trailingIcon = {
                if (email.value.isNotEmpty()) {
                    IconButton(onClick = { email.value = "" }) {
                        Icon(
                            imageVector = Icons.Rounded.Clear, contentDescription = stringResource(
                                R.string.clear_image
                            )
                        )
                    }
                }
            },
            label = { Text(text = UiText.StringResource(R.string.email).asString()) },
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { passwordTextFieldFocusRequester.requestFocus() }),
            isError = emailValidationState !is ValidationEmailResult.Success,
            supportingText = {
                AnimatedVisibility(visible = emailValidationState !is ValidationEmailResult.Success) {
                    Text(
                        text = UiText.StringResource(
                            when (emailValidationState) {
                                is ValidationEmailResult.EmailIsEmpty -> R.string.email_is_empty
                                is ValidationEmailResult.NotValidEmail -> R.string.is_not_email
                                is ValidationEmailResult.Success -> R.string.email
                            }
                        ).asString()
                    )
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
                onEvent(SignUpScreenEvent.PasswordChanged(it))
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
            isError = passwordValidationState !is ValidationPasswordResult.Success,
            supportingText = {
                AnimatedVisibility(visible = passwordValidationState !is ValidationPasswordResult.Success) {
                    Text(
                        text = UiText.StringResource(
                            when (passwordValidationState) {
                                is ValidationPasswordResult.VeryShortPassword -> R.string.password_is_short
                                is ValidationPasswordResult.NotContainsLettersOrDigits -> R.string.not_contains_letters_digits
                                is ValidationPasswordResult.NotContainsLowerOrUpperCase -> R.string.not_contains_lower_upper_letters
                                is ValidationPasswordResult.Success -> R.string.password
                            }
                        ).asString()
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
                onEvent(SignUpScreenEvent.SignUp(email.value, password.value))
                focusManager.clearFocus()
            },
            enabled = emailValidationState is ValidationEmailResult.Success &&
                    passwordValidationState is ValidationPasswordResult.Success &&
                    email.value.isNotEmpty() &&
                    password.value.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 48.dp)
        ) {
            Text(text = UiText.StringResource(R.string.sign_up).asString())
        }
    }
}

@Preview
@Composable
fun SignUpPartScreenPreview() {
    CafeAppTheme {
//        SignUpScreenPart({})
    }
}