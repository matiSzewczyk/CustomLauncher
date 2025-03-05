package com.matis.customlauncher.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.matis.customlauncher.device.LauncherPermissionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberAppState(
    launcherPermissionManager: LauncherPermissionManager,
    coroutineScope: CoroutineScope
): AppState {
    return remember(launcherPermissionManager, coroutineScope) {
        AppState(
            launcherPermissionManager = launcherPermissionManager,
            coroutineScope = coroutineScope
        )
    }
}

class AppState(
    launcherPermissionManager: LauncherPermissionManager,
    coroutineScope: CoroutineScope
) {

    val applicationState: StateFlow<LauncherState> = launcherPermissionManager.isDefaultLauncherApplication()
        .map { if (it) LauncherState.DefaultLauncher else LauncherState.NotDefaultLauncher }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LauncherState.Loading
        )
}
