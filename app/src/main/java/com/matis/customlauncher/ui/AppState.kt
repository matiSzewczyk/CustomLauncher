package com.matis.customlauncher.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.matis.customlauncher.core.data.repository.EnvironmentRepository
import kotlinx.coroutines.flow.StateFlow

@Composable
fun rememberAppState(
    repository: EnvironmentRepository,
): AppState =
    remember(repository) {
        AppState(
            repository = repository
        )
    }

class AppState(
    repository: EnvironmentRepository
) {

    val hasDefaultLauncherPermission: StateFlow<Boolean> =
        repository.isDefaultHomeApp()
}
