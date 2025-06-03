package com.matis.customlauncher.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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

fun NavGraphBuilder.settingsNavigation() {
    composable<SettingsScreen>(
        enterTransition = { scaleIn(initialScale = 0f) + fadeIn() },
        exitTransition = { scaleOut() + fadeOut() }
    ) {
        SettingsScreen()
    }
}
