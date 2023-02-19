package com.cafeapp.ui.screens.profile.signUp_flow.user_data

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import com.cafeapp.ui.screens.appDestination
import com.cafeapp.ui.screens.destinations.SignUpScreenDestination
import com.cafeapp.ui.screens.destinations.UserPhotoScreenDestination
import com.cafeapp.core.util.AnimationsConst
import com.ramcosta.composedestinations.spec.DestinationStyle

@OptIn(ExperimentalAnimationApi::class)
object UserDataScreenTransitions : DestinationStyle.Animated {
    override fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition? {
        return when (initialState.appDestination()) {
            SignUpScreenDestination -> slideIntoContainer(
                towards = AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(AnimationsConst.enterTransitionsDuration)
            )
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.exitTransition(): ExitTransition? {
        return when (targetState.appDestination()) {
            UserPhotoScreenDestination -> fadeOut(
                animationSpec = tween(AnimationsConst.exitTransitionsDuration)
            )
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popEnterTransition(): EnterTransition? {
        return when (initialState.appDestination()) {
            UserPhotoScreenDestination -> fadeIn(
                animationSpec = tween(AnimationsConst.enterTransitionsDuration)
            )
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popExitTransition(): ExitTransition? {
        return when (targetState.appDestination()) {
            SignUpScreenDestination -> slideOutOfContainer(
                towards = AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(AnimationsConst.exitTransitionsDuration)
            )
            else -> null
        }
    }
}