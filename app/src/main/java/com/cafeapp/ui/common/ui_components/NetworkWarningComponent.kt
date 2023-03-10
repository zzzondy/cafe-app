package com.cafeapp.ui.common.ui_components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cafeapp.R
import com.cafeapp.core.network.NetworkStatus
import com.cafeapp.core.util.AnimationsConst
import com.cafeapp.ui.theme.CafeAppTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NetworkWarningComponent(
    networkStatus: NetworkStatus,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = networkStatus == NetworkStatus.Unavailable,
        enter = scaleIn(tween(AnimationsConst.enterTransitionsDuration)) +
                fadeIn(tween(AnimationsConst.enterTransitionsDuration)),
        exit = scaleOut(tween((AnimationsConst.exitTransitionsDuration))) +
                fadeOut(tween(AnimationsConst.exitTransitionsDuration))
    ) {
        Box(
            modifier = modifier
                .size(48.dp)
                .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.round_wifi_off_24),
                contentDescription = stringResource(R.string.no_wifi),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
}

@Preview
@Composable
private fun NetworkWarningComponentPreview() {
    CafeAppTheme {
        NetworkWarningComponent(networkStatus = NetworkStatus.Unavailable)
    }
}

