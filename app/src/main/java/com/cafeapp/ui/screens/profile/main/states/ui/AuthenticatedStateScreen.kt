package com.cafeapp.ui.screens.profile.main.states.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.cafeapp.R
import com.cafeapp.core.util.UiText
import com.cafeapp.domain.models.User
import com.cafeapp.ui.theme.CafeAppTheme
import com.cafeapp.ui.theme.LocalImageLoader
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.crossfade.CrossfadePlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.coil.CoilImageState
import com.skydoves.landscapist.components.rememberImageComponent

@Composable
fun AuthenticatedStateScreen(
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
                width = Dimension.value(150.dp)
                height = Dimension.value(150.dp)
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

        CoilImage(
            imageModel = { user.photoUrl },
            imageOptions = ImageOptions(contentDescription = stringResource(R.string.user_photo)),
            component = rememberImageComponent {
                +CrossfadePlugin()
            },
            failure = {
                Image(
                    painter = painterResource(R.drawable.ic_round_person_24),
                    contentDescription = stringResource(R.string.user_photo),
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
            },
            modifier = Modifier
                .layoutId(AuthenticatedStateScreenTags.userPicture)
                .clip(CircleShape)
                .placeholder(
                    visible = skeletonLoadingState,
                    shape = CircleShape,
                    highlight = PlaceholderHighlight.fade(),
                    color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                ),
            onImageStateChanged = { state ->
                skeletonLoadingState = when (state) {
                    is CoilImageState.Loading -> {
                        true
                    }
                    is CoilImageState.Success -> {
                        false
                    }
                    is CoilImageState.Failure -> {
                        false
                    }
                    is CoilImageState.None -> {
                        true
                    }
                }
            },
            imageLoader = { LocalImageLoader.current }
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
                    highlight = PlaceholderHighlight.fade(),
                    color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
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
                    highlight = PlaceholderHighlight.fade(),
                    color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
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