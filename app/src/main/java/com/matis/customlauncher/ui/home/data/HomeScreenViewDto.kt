package com.matis.customlauncher.ui.home.data

import com.matis.customlauncher.model.domain.HomePageLayoutType
import com.matis.customlauncher.model.domain.HomeScreenDto
import com.matis.customlauncher.model.domain.MainPage
import com.matis.customlauncher.model.view.HomeScreenPageViewDto
import com.matis.customlauncher.model.view.toView

data class HomeScreenViewDto(
    val layoutType: HomePageLayoutType = MainPage.HOME.defaultLayout,
    val pages: List<HomeScreenPageViewDto> = emptyList()
)

fun HomeScreenDto.toView(): HomeScreenViewDto =
    HomeScreenViewDto(
        layoutType = MainPage.HOME.defaultLayout,
        pages = pages.map { it.toView() }
    )
