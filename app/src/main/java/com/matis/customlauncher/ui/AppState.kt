package com.matis.customlauncher.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.matis.customlauncher.device.PackagesApi
import kotlinx.coroutines.flow.StateFlow

@Composable
fun rememberAppState(
    packagesApi: PackagesApi
): AppState =
    remember(packagesApi) {
        AppState(
            packagesApi = packagesApi
        )
    }

class AppState(
    packagesApi: PackagesApi
) {

    val hasDefaultLauncherPermission: StateFlow<Boolean> =
        packagesApi.isDefaultHomeApp
}
