package com.cafeapp.ui.screens.profile.signUp_flow.signUp

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import com.cafeapp.ui.screens.appDestination
import com.cafeapp.ui.screens.destinations.ProfileScreenDestination
import com.cafeapp.core.util.AnimationsConst
import com.ramcosta.composedestinations.spec.DestinationStyle

@OptIn(ExperimentalAnimationApi::class)
object SignUpScreenTransitions : DestinationStyle.Animated {
    override fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition? {
        return when (initialState.appDestination()) {
            ProfileScreenDestination -> slideIntoContainer(
                towards = AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(AnimationsConst.enterTransitionsDuration)
            )
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.exitTransition(): ExitTransition {
        return fadeOut(animationSpec = tween(AnimationsConst.exitTransitionsDuration))
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popEnterTransition(): EnterTransition {
        return fadeIn(animationSpec = tween(AnimationsConst.exitTransitionsDuration))
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popExitTransition(): ExitTransition? {
        return when (targetState.appDestination()) {
            ProfileScreenDestination -> slideOutOfContainer(
                towards = AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(AnimationsConst.exitTransitionsDuration)
            )
            else -> null
        }
    }
}