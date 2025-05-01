package com.matis.customlauncher.ui.settings.data.model

import com.matis.customlauncher.model.MainPage.APP_DRAWER
import com.matis.customlauncher.model.MainPage.HOME
import com.matis.customlauncher.model.PageLayoutDto

data class UiState(
    val appliedLayoutTypeForHome: PageLayoutDto = PageLayoutDto(
        HOME.defaultLayout,
        HOME
    ),
    val appliedLayoutTypeForAppDrawer: PageLayoutDto = PageLayoutDto(
        APP_DRAWER.defaultLayout,
        APP_DRAWER
    ),
    val layoutDialogToDisplay: LayoutDialogType? = null
)
