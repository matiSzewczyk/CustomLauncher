package com.matis.customlauncher.ui

import androidx.compose.ui.graphics.Color

enum class Page(
    val color: Color,
    val pageNumber: Int
) {
    HOME(color = Color.Transparent, pageNumber = 0),
    APP_SEARCH(color = Color.Black.copy(alpha = .4f), pageNumber = 1)
}
