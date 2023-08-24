package com.cafeapp.ui.screens.profile.settings.about_app

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cafeapp.BuildConfig
import com.cafeapp.R
import com.cafeapp.core.util.UIText
import com.cafeapp.core.util.collectAsEffect
import com.cafeapp.ui.screens.profile.ProfileNavGraph
import com.cafeapp.ui.screens.profile.settings.about_app.states.AboutAppScreenEffect
import com.cafeapp.ui.screens.profile.settings.about_app.states.AboutAppScreenEvent
import com.cafeapp.ui.screens.profile.settings.about_app.states.AboutAppScreenState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@ProfileNavGraph
@Destination(
    style = AboutAppScreenTransitions::class
)
@Composable
fun AboutAppScreen(
    navigator: DestinationsNavigator,
    aboutAppScreenViewModel: AboutAppScreenViewModel = hiltViewModel()
) {
    val screenState by aboutAppScreenViewModel.screenState.collectAsState()
    aboutAppScreenViewModel.screenEffect.collectAsEffect { effect ->
        when (effect) {
            AboutAppScreenEffect.NavigateBack -> navigator.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = UIText.StringResource(R.string.about_app).asString())
                },
                navigationIcon = {
                    IconButton(onClick = { aboutAppScreenViewModel.onEvent(AboutAppScreenEvent.OnBackButtonClicked) }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = stringResource(
                                R.string.arrow_back_image
                            )
                        )
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        when (screenState) {
            AboutAppScreenState.Data -> {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_app_logo),
                        contentDescription = stringResource(
                            R.string.app_logo_image
                        ),
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.size(96.dp)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = UIText.StringResource(R.string.app_name).asString(),
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = BuildConfig.VERSION_NAME,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}