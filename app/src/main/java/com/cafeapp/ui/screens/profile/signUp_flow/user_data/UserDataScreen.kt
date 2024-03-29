package com.cafeapp.ui.screens.profile.signUp_flow.user_data

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cafeapp.R
import com.cafeapp.core.util.UIText
import com.cafeapp.core.util.collectAsEffect
import com.cafeapp.ui.screens.destinations.UserPhotoScreenDestination
import com.cafeapp.ui.screens.profile.signUp_flow.SignUpFlowNavGraph
import com.cafeapp.ui.screens.profile.signUp_flow.SignUpSharedViewModel
import com.cafeapp.ui.screens.profile.signUp_flow.user_data.states.UserDataScreenEffect
import com.cafeapp.ui.screens.profile.signUp_flow.user_data.states.UserDataScreenEvent
import com.cafeapp.ui.screens.profile.signUp_flow.user_data.states.UserDataScreenState
import com.cafeapp.ui.screens.profile.signUp_flow.user_data.utils.PHONE_MASK
import com.cafeapp.ui.screens.profile.signUp_flow.user_data.utils.PhoneVisualTransformation
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@SignUpFlowNavGraph
@Destination(style = UserDataScreenTransitions::class)
@Composable
fun UserDataScreen(
    navigator: DestinationsNavigator,
    signUpSharedViewModel: SignUpSharedViewModel,
    userDataScreenViewModel: UserDataScreenViewModel = hiltViewModel()
) {
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }

    val screenState by userDataScreenViewModel.userDataScreenState.collectAsState()
    userDataScreenViewModel.userDataScreenEffect.collectAsEffect { effect ->
        when (effect) {
            UserDataScreenEffect.NavigateOnUserPhotoScreen -> {
                signUpSharedViewModel.updateFirstLastNameAndPhone(
                    firstName, lastName, phoneNumber
                )
                navigator.navigate(UserPhotoScreenDestination)
            }

            UserDataScreenEffect.NavigateBack -> navigator.popBackStack()
        }
    }

    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = UIText.StringResource(R.string.user_data_title).asString()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { userDataScreenViewModel.onEvent(UserDataScreenEvent.OnBackButtonClicked) }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = stringResource(
                                R.string.arrow_back_image
                            )
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { focusManager.clearFocus() }
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = UIText.StringResource(R.string.user_data_hint).asString(),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            )

            OutlinedTextField(
                value = firstName,
                onValueChange = {
                    firstName = it
                    userDataScreenViewModel.onEvent(
                        UserDataScreenEvent.FirstNameFieldChanged(
                            firstName
                        )
                    )
                },
                label = { Text(text = UIText.StringResource(R.string.first_name).asString()) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Person, contentDescription = stringResource(
                            R.string.person_image
                        )
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = {
                    lastName = it
                    userDataScreenViewModel.onEvent(
                        UserDataScreenEvent.LastNameFieldChanged(
                            lastName
                        )
                    )
                },
                label = { Text(text = UIText.StringResource(R.string.last_name).asString()) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Person, contentDescription = stringResource(
                            R.string.person_image
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            )

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { newPhone ->
                    phoneNumber = newPhone.take(PHONE_MASK.count { it == '0' })
                    userDataScreenViewModel.onEvent(
                        UserDataScreenEvent.PhoneNumberFieldChanged(
                            phoneNumber
                        )
                    )
                },
                label = { Text(text = UIText.StringResource(R.string.phone_number).asString()) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Phone, contentDescription = stringResource(
                            R.string.phone_image
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Phone
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                visualTransformation = PhoneVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            )
            Button(
                onClick = {
                    userDataScreenViewModel.onEvent(UserDataScreenEvent.OnNextButtonClicked)
                },
                enabled = screenState is UserDataScreenState.EnabledNavigationButton,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = UIText.StringResource(R.string.next).asString())
            }
        }
    }
}