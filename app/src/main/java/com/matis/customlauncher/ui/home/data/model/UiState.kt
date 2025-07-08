package com.matis.customlauncher.ui.home.data.model

import com.matis.customlauncher.model.view.HomeScreenViewDto

data class UiState(
    val isInEditMode: Boolean = false,
    val homeScreen: HomeScreenViewDto = HomeScreenViewDto()
)
