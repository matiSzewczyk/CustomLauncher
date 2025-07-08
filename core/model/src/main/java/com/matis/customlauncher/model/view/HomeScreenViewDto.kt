package com.matis.customlauncher.model.view

import com.matis.customlauncher.model.domain.ApplicationIconConfigDto
import com.matis.customlauncher.model.domain.HomePageLayoutType
import com.matis.customlauncher.model.domain.HomeScreenDto
import com.matis.customlauncher.model.domain.MainPage

data class HomeScreenViewDto(
    val layoutType: HomePageLayoutType = MainPage.HOME.defaultLayout,
    val applicationIconConfig: ApplicationIconConfigDto = ApplicationIconConfigDto.defaults,
    val pages: List<HomeScreenPageViewDto> = emptyList()
)

fun HomeScreenDto.toView(): HomeScreenViewDto =
    HomeScreenViewDto(
        layoutType = layoutType,
        applicationIconConfig = applicationIconConfig,
        pages = pages.map { it.toView() }
    )
