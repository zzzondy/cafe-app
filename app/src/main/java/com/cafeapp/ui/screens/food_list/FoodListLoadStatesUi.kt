package com.cafeapp.ui.screens.food_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cafeapp.R
import com.cafeapp.ui.util.UiText

@Composable
fun ErrorWhenRefresh() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_round_signal_wifi_off_24),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            contentDescription = stringResource(R.string.no_wifi),
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = UiText.StringResource(R.string.some_error).asString(),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = UiText.StringResource(R.string.refresh_error).asString(),
            style = MaterialTheme.typography.titleSmall,
            maxLines = 2,
            textAlign = TextAlign.Center
        )

    }
}

@Composable
fun ErrorWhenAppend() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = UiText.StringResource(R.string.refresh_error).asString(),
            style = MaterialTheme.typography.titleSmall
        )
    }
}