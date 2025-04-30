package com.matis.customlauncher.ui.main

import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.matis.customlauncher.ui.AppState

@Composable
fun MainScreen(
    appState: AppState,
    onSettingsClicked: () -> Unit
) {
    val hasDefaultLauncherPermission by appState.isSetAsDefaultHomeApplication.collectAsStateWithLifecycle()
    val context = LocalContext.current
    BackHandler {
        if (hasDefaultLauncherPermission) Unit
        else (context as? MainActivity)?.onBackPressedDispatcher?.onBackPressed()
    }
    if (!hasDefaultLauncherPermission) NotDefaultHomeAppContent(
        onSetDefaultHomeApplicationClicked = { context.startActivity(Intent(Settings.ACTION_HOME_SETTINGS)) }
    )
    else MainScreenContent(onSettingsClicked = onSettingsClicked)
}
