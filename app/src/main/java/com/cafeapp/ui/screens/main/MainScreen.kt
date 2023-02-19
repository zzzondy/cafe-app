package com.cafeapp.ui.screens.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.cafeapp.core.util.AnimationsConst
import com.cafeapp.ui.screens.NavGraphs
import com.cafeapp.ui.screens.profile.signUp_flow.SignUpSharedViewModel
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class,
    ExperimentalMaterialNavigationApi::class
)
@Composable
fun MainScreen() {
    val animatedNavHostEngine = rememberAnimatedNavHostEngine(
        navHostContentAlignment = Alignment.TopCenter,
        rootDefaultAnimations = RootNavGraphDefaultAnimations(
            enterTransition = { fadeIn(tween(AnimationsConst.enterTransitionsDuration)) },
            exitTransition = { fadeOut(tween(AnimationsConst.exitTransitionsDuration)) }
        )
    )
    val navController = animatedNavHostEngine.rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            navController = navController,
            engine = animatedNavHostEngine,
            modifier = Modifier
                .padding(
                    start = paddingValues.calculateStartPadding(
                        LocalLayoutDirection.current
                    ), end = paddingValues.calculateEndPadding(
                        LocalLayoutDirection.current
                    )
                ),
            dependenciesContainerBuilder = {
                dependency(NavGraphs.signUpFlow) {
                    val parentEntry = remember(navBackStackEntry) {
                        navController.getBackStackEntry(NavGraphs.signUpFlow.route)
                    }

                    hiltViewModel<SignUpSharedViewModel>(parentEntry)
                }
            }
        )
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}