package com.matis.customlauncher.ui.main.data.model

import androidx.compose.ui.graphics.Color
import com.matis.customlauncher.model.domain.MainPage

data class MainPageConfig(
    val type: MainPage,
    val color: Color
)

val mainPageConfigs = listOf(
    MainPageConfig(
        type = MainPage.HOME,
        color = Color.Transparent
    ),
    MainPageConfig(
        type = MainPage.APP_DRAWER,
        color = Color.Black.copy(alpha = .6f)
    )
)

val colorForPage: (Int) -> Color = { position ->
    mainPageConfigs[MainPage.entries[position].pageNumber].color
}
