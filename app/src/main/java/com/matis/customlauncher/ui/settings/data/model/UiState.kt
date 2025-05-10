package com.matis.customlauncher.ui.settings.data.model

import com.matis.customlauncher.model.domain.MainPage.APP_DRAWER
import com.matis.customlauncher.model.domain.MainPage.HOME
import com.matis.customlauncher.model.domain.PageLayoutDto

data class UiState(
    val appliedLayoutTypeForHome: PageLayoutDto = PageLayoutDto(
        HOME.defaultLayout,
        HOME
    ),
    val appliedLayoutTypeForAppDrawer: PageLayoutDto = PageLayoutDto(
        APP_DRAWER.defaultLayout,
        APP_DRAWER
    ),
    val layoutDialogToDisplay: LayoutDialogType? = null,
    val showApplicationLabel: Boolean = true,
)
