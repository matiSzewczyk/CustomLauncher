package com.matis.customlauncher.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.matis.customlauncher.ui.AppState
import com.matis.customlauncher.ui.main.MainScreen
import kotlinx.serialization.Serializable

@Serializable
data object MainNavGraph

@Serializable
data object MainScreen

fun NavGraphBuilder.mainNavGraph(appState: AppState) {
    navigation<MainNavGraph>(startDestination = MainScreen) {
        composable<MainScreen> {
            MainScreen(appState = appState)
        }
    }
}
