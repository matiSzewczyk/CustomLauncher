package com.matis.customlauncher.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.matis.customlauncher.ui.AppState

@Composable
fun MainNavHost(
    appState: AppState
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainNavGraph
    ) {
        mainNavGraph(
            appState = appState,
            navController = navController
        )
    }
}
