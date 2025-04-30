package com.matis.customlauncher.ui.home.data

import com.matis.customlauncher.model.HomeScreenPage
import com.matis.customlauncher.model.LayoutType
import com.matis.customlauncher.model.MainPage

data class HomeScreenViewDto(
    val layoutType: LayoutType = MainPage.HOME.defaultLayout,
    val pages: List<HomeScreenPage> = emptyList()
)
