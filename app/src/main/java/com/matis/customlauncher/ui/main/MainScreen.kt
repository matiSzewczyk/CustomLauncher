package com.matis.customlauncher.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.matis.customlauncher.ui.AppState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent.copy(alpha = .15f)
    ) {
        if (!hasDefaultLauncherPermission) NotDefaultHomeAppContent(
            onSetDefaultHomeApplicationClicked = { context.startActivity(Intent(Settings.ACTION_HOME_SETTINGS)) }
        )
        else MainScreenContent(onSettingsClicked = onSettingsClicked)
    }
}
