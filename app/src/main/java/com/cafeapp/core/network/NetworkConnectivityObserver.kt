package com.cafeapp.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class NetworkConnectivityObserver(context: Context) : ConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    override fun observe(): Flow<NetworkStatus> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                trySend(NetworkStatus.Available)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                trySend(NetworkStatus.Unavailable)
            }
        }

        connectivityManager.registerNetworkCallback(networkRequest, callback)

        val currentState = getCurrentConnectivityState(connectivityManager)
        trySend(currentState)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }
}

val Context.currentConnectivityState: NetworkStatus
    get() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return getCurrentConnectivityState(connectivityManager)
    }

private fun getCurrentConnectivityState(
    connectivityManager: ConnectivityManager
): NetworkStatus {
    val connected = connectivityManager.allNetworks.any { network ->
        connectivityManager.getNetworkCapabilities(network)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }

    return if (connected) NetworkStatus.Available else NetworkStatus.Unavailable
}