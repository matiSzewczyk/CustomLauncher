package com.matis.customlauncher.ui

sealed interface LauncherState {
    data object DefaultLauncher : LauncherState
    data object NotDefaultLauncher : LauncherState
    data object Loading : LauncherState

    fun shouldShowSplashScreen(): Boolean = this is Loading
}
