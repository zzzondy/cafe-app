package com.cafeapp.ui.screens.profile.settings.about_app

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import com.cafeapp.core.util.AnimationsConst
import com.cafeapp.ui.screens.appDestination
import com.cafeapp.ui.screens.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.spec.DestinationStyle

@OptIn(ExperimentalAnimationApi::class)
object AboutAppScreenTransitions : DestinationStyle.Animated {

    override fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition? {
        return when (initialState.appDestination()) {
            SettingsScreenDestination -> slideIntoContainer(
                towards = AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(AnimationsConst.enterTransitionsDuration)
            )
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.exitTransition(): ExitTransition? {
        return null
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popEnterTransition(): EnterTransition? {
        return null
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popExitTransition(): ExitTransition? {
        return when (targetState.appDestination()) {
            SettingsScreenDestination -> slideOutOfContainer(
                towards = AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(AnimationsConst.exitTransitionsDuration)
            )
            else -> null
        }
    }
}