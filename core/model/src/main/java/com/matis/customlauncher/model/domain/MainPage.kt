package com.matis.customlauncher.model.domain

enum class MainPage(
    val pageNumber: Int,
    val defaultLayout: HomePageLayoutType
) {
    HOME(pageNumber = 0, defaultLayout = HomePageLayoutType.GRID_3X4),
    APP_DRAWER(pageNumber = 1, defaultLayout = HomePageLayoutType.LIST)
}
