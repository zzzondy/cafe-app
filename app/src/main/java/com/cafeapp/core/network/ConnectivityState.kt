package com.cafeapp.core.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import com.cafeapp.ui.screens.app.LocalConnectivityObserver
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun rememberNetworkStatus(): State<NetworkStatus> {
    val context = LocalContext.current
    val connectivityObserver = LocalConnectivityObserver.current

    return produceState(initialValue = context.currentConnectivityState) {
        connectivityObserver.observe().onEach { value = it }.collect()
    }
}