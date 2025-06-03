package com.matis.customlauncher.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.matis.customlauncher.ui.AppState
import kotlinx.serialization.Serializable

@Serializable
data object MainNavGraph

fun NavGraphBuilder.mainNavGraph(
    appState: AppState,
    navController: NavController
) {
    navigation<MainNavGraph>(startDestination = MainScreen) {
        mainNavigation(
            appState = appState,
            navController = navController
        )
        settingsNavigation()
    }
}
