package com.cafeapp.ui.screens.profile.main

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import com.cafeapp.core.util.AnimationsConst
import com.cafeapp.ui.screens.appDestination
import com.cafeapp.ui.screens.destinations.LoginScreenDestination
import com.cafeapp.ui.screens.destinations.SettingsScreenDestination
import com.cafeapp.ui.screens.destinations.SignUpScreenDestination
import com.ramcosta.composedestinations.spec.DestinationStyle

@OptIn(ExperimentalAnimationApi::class)
object ProfileScreenTransitions : DestinationStyle.Animated {
    override fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition? {
        return null
    }

    override fun AnimatedContentScope<NavBackStackEntry>.exitTransition(): ExitTransition? {
        return when (targetState.appDestination()) {
            SettingsScreenDestination -> fadeOut(animationSpec = tween(AnimationsConst.exitTransitionsDuration))
            LoginScreenDestination -> fadeOut(animationSpec = tween(AnimationsConst.exitTransitionsDuration))
            SignUpScreenDestination -> fadeOut(animationSpec = tween(AnimationsConst.exitTransitionsDuration))
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popEnterTransition(): EnterTransition? {
        return when (initialState.appDestination()) {
            SettingsScreenDestination -> fadeIn(animationSpec = tween(AnimationsConst.exitTransitionsDuration))
            LoginScreenDestination -> fadeIn(animationSpec = tween(AnimationsConst.exitTransitionsDuration))
            SignUpScreenDestination -> fadeIn(animationSpec = tween(AnimationsConst.exitTransitionsDuration))
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popExitTransition(): ExitTransition? {
        return null
    }
}