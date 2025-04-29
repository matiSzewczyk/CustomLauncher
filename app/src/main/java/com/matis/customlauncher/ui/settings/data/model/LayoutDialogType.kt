package com.matis.customlauncher.ui.settings.data.model

import com.matis.customlauncher.model.LayoutType
import com.matis.customlauncher.model.LayoutType.GRID_3X4
import com.matis.customlauncher.model.LayoutType.GRID_3X5
import com.matis.customlauncher.model.LayoutType.GRID_4X4

sealed class LayoutDialogType(val supportedLayouts: List<LayoutType>) {

    data object None : LayoutDialogType(
        supportedLayouts = emptyList()
    )

    data object HomeScreen : LayoutDialogType(
        supportedLayouts = listOf(GRID_3X4, GRID_3X5, GRID_4X4)
    )

    data object AppDrawer : LayoutDialogType(
        supportedLayouts = LayoutType.entries
    )
}
