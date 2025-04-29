package com.matis.customlauncher.model

enum class MainPage(
    val pageNumber: Int,
    val defaultLayout: LayoutType
) {
    HOME(pageNumber = 0, defaultLayout = LayoutType.GRID_3X4),
    APP_DRAWER(pageNumber = 1, defaultLayout = LayoutType.LIST)
}
