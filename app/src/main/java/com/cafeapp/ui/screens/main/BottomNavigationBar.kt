package com.cafeapp.ui.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.cafeapp.core.util.AnimationsConst
import com.cafeapp.ui.screens.NavGraphs
import com.cafeapp.ui.screens.appCurrentDestinationAsState
import com.cafeapp.ui.screens.startAppDestination
import com.ramcosta.composedestinations.navigation.navigate

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentDestination =
        navController.appCurrentDestinationAsState().value ?: NavGraphs.root.startAppDestination

    val bottomBarItems = BottomBarDestinations.values()

    AnimatedVisibility(
        visible = bottomBarItems.any { it.graph.startRoute == currentDestination } ,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(AnimationsConst.enterTransitionsDuration)
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(AnimationsConst.exitTransitionsDuration)
        ),
    ) {
        NavigationBar {
            bottomBarItems.forEach { graph ->
                NavigationBarItem(
                    selected = currentDestination == graph.graph.startRoute,
                    onClick = {
                        if (currentDestination == graph.graph.startRoute) {
                            return@NavigationBarItem
                        }
                        navController.navigate(graph.graph) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = graph.icon,
                            contentDescription = stringResource(graph.label)
                        )
                    },
                    label = {
                        Text(text = stringResource(id = graph.label))
                    }
                )
            }
        }
    }
}