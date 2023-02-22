package com.cafeapp.ui.screens.food_list.main.states.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cafeapp.R
import com.cafeapp.core.util.UiText


@Composable
fun ErrorWhenAppend(modifier: Modifier = Modifier, onRefresh: () -> Unit = {}) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = UiText.StringResource(R.string.refresh_error).asString(),
            style = MaterialTheme.typography.titleSmall,
            maxLines = 2
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onRefresh) {
            Text(text = UiText.StringResource(R.string.refresh).asString())
        }
    }
}