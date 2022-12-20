package com.cafeapp.ui.screens.main

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.cafeapp.ui.screens.NavGraphs
import com.cafeapp.ui.screens.appCurrentDestinationAsState
import com.cafeapp.ui.screens.route.BottomBarDestinations
import com.cafeapp.ui.screens.startAppDestination
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.navigate

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class,
    ExperimentalMaterialNavigationApi::class
)
@Composable
fun MainScreen() {
    val navController = rememberAnimatedNavController()
    val animatedNavHostEngine = rememberAnimatedNavHostEngine(
        navHostContentAlignment = Alignment.TopCenter,
        rootDefaultAnimations = RootNavGraphDefaultAnimations(
            enterTransition = { fadeIn(tween(300)) },
            exitTransition = { fadeOut(tween(300)) },
            popEnterTransition = { fadeIn(tween(300)) },
            popExitTransition = { fadeOut(tween(300)) }
        )
    )
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            navController = navController,
            engine = animatedNavHostEngine,
            modifier = Modifier.padding(
                start = paddingValues.calculateStartPadding(
                    LocalLayoutDirection.current
                ), end = paddingValues.calculateEndPadding(
                    LocalLayoutDirection.current
                ), top = paddingValues.calculateTopPadding(), bottom = 80.dp
            )
        )
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentDestination =
        navController.appCurrentDestinationAsState().value ?: NavGraphs.root.startAppDestination

    val bottomBarItems = BottomBarDestinations.values()
    AnimatedVisibility(
        visible = bottomBarItems.any { it.direction == currentDestination },
        enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(300)),
        exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(300))
    ) {
        NavigationBar {
            bottomBarItems.forEach { destination ->
                NavigationBarItem(
                    selected = currentDestination == destination.direction,
                    onClick = {
                        navController.navigate(destination.direction) {
                            launchSingleTop = true
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(imageVector = destination.icon, contentDescription = null)
                    },
                    label = {
                        Text(text = stringResource(id = destination.label))
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}