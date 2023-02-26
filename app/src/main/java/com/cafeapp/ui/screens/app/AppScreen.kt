package com.cafeapp.ui.screens.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.cafeapp.core.network.ConnectivityObserver
import com.cafeapp.core.network.NetworkConnectivityObserver
import com.cafeapp.ui.screens.main.MainScreen


val LocalImageLoader = staticCompositionLocalOf<ImageLoader> { error("No provided image loader") }

val LocalConnectivityObserver =
    staticCompositionLocalOf<ConnectivityObserver> { error("No connectivity observer provided") }

@Composable
fun AppScreen() {
    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context)
        .memoryCache {
            MemoryCache.Builder(context)
                .maxSizePercent(0.25)
                .build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizePercent(0.02)
                .build()
        }
        .build()

    val connectivityObserver = NetworkConnectivityObserver(context)

    CompositionLocalProvider(
        LocalImageLoader provides imageLoader,
        LocalConnectivityObserver provides connectivityObserver
    ) {
        MainScreen()
    }
}