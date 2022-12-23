package com.cafeapp.ui.screens.profile

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import com.cafeapp.ui.screens.appDestination
import com.cafeapp.ui.screens.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.spec.DestinationStyle

@OptIn(ExperimentalAnimationApi::class)
object ProfileScreenTransitions : DestinationStyle.Animated {
    override fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition? {
        return when (initialState.appDestination()) {
            LoginScreenDestination -> slideInHorizontally(
                initialOffsetX = { 1000 },
                animationSpec = tween(300)
            )
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.exitTransition(): ExitTransition? {
        return when (targetState.appDestination()) {
            LoginScreenDestination -> slideOutHorizontally(
                targetOffsetX = { -1000 },
                animationSpec = tween(300)
            )
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popEnterTransition(): EnterTransition? {
        return when (initialState.appDestination()) {
            LoginScreenDestination -> slideInHorizontally(
                initialOffsetX = { -1000 },
                animationSpec = tween(300)
            )
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popExitTransition(): ExitTransition? {
        return when (targetState.appDestination()) {
            LoginScreenDestination -> slideOutHorizontally(
                targetOffsetX = { 1000 },
                animationSpec = tween(300)
            )
            else -> null
        }
    }
}