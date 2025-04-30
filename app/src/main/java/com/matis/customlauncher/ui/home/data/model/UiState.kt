package com.matis.customlauncher.ui.home.data.model

import com.matis.customlauncher.model.LayoutType
import com.matis.customlauncher.model.MainPage
import com.matis.customlauncher.ui.home.data.HomeScreenViewDto

data class UiState(
    val isInEditMode: Boolean = false,
    val layoutType: LayoutType = MainPage.HOME.defaultLayout,
    val homeScreen: HomeScreenViewDto = HomeScreenViewDto()
)
