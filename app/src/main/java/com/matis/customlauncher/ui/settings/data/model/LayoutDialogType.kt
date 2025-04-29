package com.matis.customlauncher.ui.settings.data.model

import com.matis.customlauncher.model.LayoutType
import com.matis.customlauncher.model.LayoutType.GRID_3X4
import com.matis.customlauncher.model.LayoutType.GRID_3X5
import com.matis.customlauncher.model.LayoutType.GRID_4X4
import com.matis.customlauncher.model.MainPage

sealed class LayoutDialogType(
    val page: MainPage,
    val supportedLayouts: List<LayoutType>
) {

    data object Home : LayoutDialogType(
        page = MainPage.HOME,
        supportedLayouts = listOf(GRID_3X4, GRID_3X5, GRID_4X4)
    )

    data object AppDrawer : LayoutDialogType(
        page = MainPage.APP_DRAWER,
        supportedLayouts = LayoutType.entries
    )
}
