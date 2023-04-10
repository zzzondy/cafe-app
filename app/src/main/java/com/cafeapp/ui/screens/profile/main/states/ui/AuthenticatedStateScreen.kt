package com.cafeapp.ui.screens.profile.main.states.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.cafeapp.R
import com.cafeapp.core.util.UIText
import com.cafeapp.core.util.toPxWithDensity
import com.cafeapp.domain.models.User
import com.cafeapp.ui.screens.app.LocalImageLoader
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.crossfade.CrossfadePlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.coil.CoilImageState
import com.skydoves.landscapist.components.rememberImageComponent

@Composable
fun AuthenticatedStateScreen(
    user: User,
    onOrdersListClicked: () -> Unit = {}
) {
    var skeletonLoadingState by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
                imageOptions = ImageOptions(
                    contentDescription = stringResource(R.string.user_photo),
                    requestSize = IntSize(
                        width = 75.dp.toPxWithDensity(),
                        height = 75.dp.toPxWithDensity()
                    )
                ),
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
                    .size(150.dp)
                    .clip(CircleShape)
                    .placeholder(
                        visible = skeletonLoadingState,
                        shape = CircleShape,
                        highlight = PlaceholderHighlight.shimmer(),
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
                    UIText.StringResource(R.string.user_name).asString()
                } else {
                    UIText.DynamicText(user.displayName!!).asString()
                },
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .placeholder(
                        visible = skeletonLoadingState,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                        shape = ShapeDefaults.Small
                    )
                    .layoutId(AuthenticatedStateScreenTags.userName)
            )

            Text(
                text = UIText.DynamicText(user.email).asString(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .placeholder(
                        visible = skeletonLoadingState,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                        shape = ShapeDefaults.Small
                    )
                    .layoutId(AuthenticatedStateScreenTags.userEmail)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            ListItem(
                headlineText = {
                    Text(text = UIText.StringResource(R.string.orders_list).asString())
                },
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                        2.dp
                    )
                ),
                modifier = Modifier
                    .clickable {
                        onOrdersListClicked()
                    }
            )
        }
    }
}

private object AuthenticatedStateScreenTags {
    const val userPicture = "userPicture"
    const val userName = "userName"
    const val userEmail = "userEmail"
}