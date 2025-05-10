package com.matis.customlauncher.model.domain

data class HomeScreenDto(
    val layoutType: HomePageLayoutType,
    val applicationIconConfig: ApplicationIconConfigDto,
    val pages: List<HomeScreenPageDto>
)
