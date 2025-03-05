package com.matis.customlauncher.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.matis.customlauncher.device.PackageManagerWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberAppState(
    packageManagerWrapper: PackageManagerWrapper,
    coroutineScope: CoroutineScope
): AppState =
    remember(packageManagerWrapper, coroutineScope) {
        AppState(
            packageManagerWrapper = packageManagerWrapper,
            coroutineScope = coroutineScope
        )
    }

class AppState(
    packageManagerWrapper: PackageManagerWrapper,
    coroutineScope: CoroutineScope
) {

    val hasDefaultLauncherPermission: StateFlow<Boolean> =
        packageManagerWrapper.isDefaultLauncherApplication()
            .stateIn(
                scope = coroutineScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false
            )
}
