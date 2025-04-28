package com.matis.customlauncher.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.matis.customlauncher.ui.settings.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
data object SettingsScreen

fun NavController.navigateToSettings() {
    navigate(SettingsScreen)
}

fun NavGraphBuilder.settingsNavigation(
    onBackPressed: () -> Unit
) {
    composable<SettingsScreen> {
        SettingsScreen(onBackPressed = onBackPressed)
    }
}
