package com.matis.customlauncher.ui.home.data.model

import com.matis.customlauncher.model.HomeScreenApplicationViewItem

data class UiState(
    val applications: List<HomeScreenApplicationViewItem> = emptyList()
)
