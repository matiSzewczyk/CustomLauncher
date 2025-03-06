package com.matis.customlauncher.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.matis.customlauncher.device.PackageManagerWrapper
import kotlinx.coroutines.flow.StateFlow

@Composable
fun rememberAppState(
    packageManagerWrapper: PackageManagerWrapper
): AppState =
    remember(packageManagerWrapper) {
        AppState(
            packageManagerWrapper = packageManagerWrapper
        )
    }

class AppState(
    packageManagerWrapper: PackageManagerWrapper
) {

    val hasDefaultLauncherPermission: StateFlow<Boolean> =
        packageManagerWrapper.isDefaultLauncherApplication
}
