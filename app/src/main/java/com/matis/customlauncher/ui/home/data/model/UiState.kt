package com.matis.customlauncher.ui.home.data.model

import com.matis.customlauncher.ui.home.data.HomeScreenViewDto

data class UiState(
    val isInEditMode: Boolean = false,
    val homeScreen: HomeScreenViewDto = HomeScreenViewDto()
)
