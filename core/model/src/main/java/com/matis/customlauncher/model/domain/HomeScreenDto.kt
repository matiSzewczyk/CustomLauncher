package com.matis.customlauncher.model.domain

data class HomeScreenDto(
    val layoutType: HomePageLayoutType,
    val pages: List<HomeScreenPageDto>
)
