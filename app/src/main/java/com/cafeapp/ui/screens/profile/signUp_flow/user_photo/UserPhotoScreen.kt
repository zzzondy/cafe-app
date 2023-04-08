package com.cafeapp.ui.screens.profile.signUp_flow.user_photo

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.cafeapp.R
import com.cafeapp.core.util.UIText
import com.cafeapp.core.util.collectAsEffect
import com.cafeapp.ui.common.ui_components.LoadingDialog
import com.cafeapp.ui.screens.destinations.ProfileScreenDestination
import com.cafeapp.ui.screens.profile.signUp_flow.SignUpFlowNavGraph
import com.cafeapp.ui.screens.profile.signUp_flow.SignUpSharedViewModel
import com.cafeapp.ui.screens.profile.signUp_flow.user_photo.states.UserPhotoScreenEffect
import com.cafeapp.ui.screens.profile.signUp_flow.user_photo.states.UserPhotoScreenEvent
import com.cafeapp.ui.screens.profile.signUp_flow.user_photo.states.UserPhotoScreenState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

@OptIn(ExperimentalMaterial3Api::class)
@SignUpFlowNavGraph
@Destination(style = UserPhotoScreenTransitions::class)
@Composable
fun UserPhotoScreen(
    navigator: DestinationsNavigator,
    signUpSharedViewModel: SignUpSharedViewModel,
    userPhotoScreenViewModel: UserPhotoScreenViewModel = hiltViewModel()
) {
    val screenState by userPhotoScreenViewModel.userPhotoScreenState.collectAsState()

    var isLoadingDialogActive by remember { mutableStateOf(false) }

    userPhotoScreenViewModel.userPhotoScreenEffect.collectAsEffect { effect ->
        when (effect) {
            UserPhotoScreenEffect.NavigateBackOnSuccessfulSigning ->
                navigator.popBackStack(
                    ProfileScreenDestination,
                    inclusive = false
                )

            UserPhotoScreenEffect.NavigateBack -> navigator.popBackStack()

            UserPhotoScreenEffect.ShowLoadingDialog -> isLoadingDialogActive = true

            UserPhotoScreenEffect.HideLoadingDialog -> isLoadingDialogActive = false
        }
    }

    if (isLoadingDialogActive) {
        LoadingDialog()
    }

    var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                selectedImageUri = uri
            }

            signUpSharedViewModel.updatePhotoUri(selectedImageUri, context)
        }
    )

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = UIText.StringResource(R.string.upload_profile_photo).asString()
                    )
                },
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
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                ImageSection(
                    selectedImageUri = selectedImageUri,
                    onClearImage = {
                        selectedImageUri = null
                        signUpSharedViewModel.updatePhotoUri(null, context)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                )
            }

            item {
                AnimatedVisibility(
                    visible = screenState is UserPhotoScreenState.SomeError,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { -it })
                ) {
                    Text(
                        text = if (screenState is UserPhotoScreenState.SomeError) {
                            (screenState as UserPhotoScreenState.SomeError).message.asString()
                        } else {
                            ""
                        },
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }

            item {
                ButtonsSection(
                    pickFromGallery = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    },
                    takePhoto = {},
                    onEvent = userPhotoScreenViewModel::onEvent,
                    signUpSharedViewModel = signUpSharedViewModel
                )
            }
        }
    }
}

@Composable
fun ImageSection(selectedImageUri: Uri?, onClearImage: () -> Unit, modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.TopEnd, modifier = modifier) {
        CoilImage(
            imageModel = { selectedImageUri },
            imageOptions = ImageOptions(contentDescription = stringResource(R.string.profile_photo)),
            failure = {
                Image(
                    painter = painterResource(R.drawable.ic_round_image_24),
                    contentDescription = stringResource(
                        R.string.profile_photo
                    ),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        if (selectedImageUri != null) {
            FilledTonalIconButton(onClick = onClearImage, modifier = Modifier.padding(16.dp)) {
                Icon(
                    imageVector = Icons.Rounded.Clear,
                    contentDescription = stringResource(R.string.clear_image)
                )
            }
        }

    }
}

@Composable
private fun ButtonsSection(
    pickFromGallery: () -> Unit,
    takePhoto: () -> Unit,
    onEvent: (UserPhotoScreenEvent) -> Unit,
    signUpSharedViewModel: SignUpSharedViewModel
) {
    ConstraintLayout(
        constraintSet = ConstraintSet {
            val pickPhotoButton =
                createRefFor(ButtonsConstraintTags.pickPhotoFromGalleryButton)
            val takePhotoButton =
                createRefFor(ButtonsConstraintTags.takePhotoButton)

            val signUpButton = createRefFor(ButtonsConstraintTags.signUpButton)

            constrain(pickPhotoButton) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top, 32.dp)
            }

            constrain(takePhotoButton) {
                start.linkTo(pickPhotoButton.start)
                end.linkTo(pickPhotoButton.end)
                top.linkTo(pickPhotoButton.bottom, 8.dp)
                width = Dimension.fillToConstraints
            }

            constrain(signUpButton) {
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
                top.linkTo(takePhotoButton.bottom, 16.dp)
                width = Dimension.fillToConstraints
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = pickFromGallery, modifier = Modifier
                .layoutId(ButtonsConstraintTags.pickPhotoFromGalleryButton)
        ) {
            Text(
                text = UIText.StringResource(R.string.pick_photo_from_gallery)
                    .asString()
            )
        }

        OutlinedButton(
            onClick = takePhoto,
            modifier = Modifier
                .layoutId(ButtonsConstraintTags.takePhotoButton)
        ) {
            Text(text = UIText.StringResource(R.string.take_photo).asString())
        }

        Button(
            onClick = {
                onEvent(
                    UserPhotoScreenEvent.SignUp(
                        signUpParams = signUpSharedViewModel.signUpParams
                    )
                )
            },
            modifier = Modifier
                .layoutId(ButtonsConstraintTags.signUpButton)
        ) {
            Text(text = UIText.StringResource(R.string.sign_up).asString())
        }
    }
}

private object ButtonsConstraintTags {
    const val pickPhotoFromGalleryButton = "pickPhotoFromGallery"
    const val takePhotoButton = "takePhoto"
    const val signUpButton = "signUpButton"
}

