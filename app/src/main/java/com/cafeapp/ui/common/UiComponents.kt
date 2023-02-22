package com.cafeapp.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.cafeapp.R
import com.cafeapp.core.util.UiText

@Composable
fun LoadingDialog() {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            modifier = Modifier
                .size(75.dp)
                .background(
                    MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                    shape = MaterialTheme.shapes.extraLarge
                ),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun SomeErrorState(modifier: Modifier = Modifier, onRefresh: () -> Unit = {}) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.round_wifi_off_24),
            contentDescription = stringResource(R.string.no_wifi),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = UiText.StringResource(R.string.some_error).asString(),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = UiText.StringResource(R.string.refresh_error).asString(),
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onRefresh) {
            Text(text = UiText.StringResource(R.string.refresh).asString())
        }
    }
}

@Composable
fun NetworkErrorState(modifier: Modifier = Modifier, onRefresh: () -> Unit = {}) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.round_wifi_off_24),
            contentDescription = stringResource(R.string.no_wifi),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = UiText.StringResource(R.string.no_connection).asString(),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = UiText.StringResource(R.string.no_connection_details).asString(),
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onRefresh) {
            Text(text = UiText.StringResource(R.string.refresh).asString())
        }
    }
}