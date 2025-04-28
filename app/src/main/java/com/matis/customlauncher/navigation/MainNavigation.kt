package com.matis.customlauncher.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.matis.customlauncher.ui.AppState
import com.matis.customlauncher.ui.main.MainScreen
import kotlinx.serialization.Serializable

@Serializable
data object MainScreen

fun NavGraphBuilder.mainNavigation(
    appState: AppState,
    navController: NavController
) {
    composable<MainScreen> {
        MainScreen(
            appState = appState,
            onSettingsClicked = navController::navigateToSettings
        )
    }
}

