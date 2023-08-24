package com.cafeapp.ui.screens.profile.main.states.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cafeapp.R
import com.cafeapp.core.util.UIText
import com.cafeapp.ui.theme.CafeAppTheme

@Composable
fun NotAuthenticatedStateScreen(onSignInClick: () -> Unit, onSignUpClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = UIText.StringResource(R.string.profile_explanation).asString(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
        )

        Button(
            onClick = onSignInClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(text = UIText.StringResource(R.string.sign_in).asString())
        }

        OutlinedButton(
            onClick = onSignUpClick, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
        ) {
            Text(text = UIText.StringResource(R.string.sign_up).asString())
        }

    }
}

@Preview
@Composable
fun NotAuthenticatedStateScreenPreview() {
    CafeAppTheme {
        NotAuthenticatedStateScreen(onSignInClick = {}, onSignUpClick = {})
    }
}