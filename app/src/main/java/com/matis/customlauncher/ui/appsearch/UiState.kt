package com.matis.customlauncher.ui.appsearch

import com.matis.customlauncher.domain.data.model.HomeScreenApplicationDto

data class UiState(
    val query: String = "",
    val applications: List<HomeScreenApplicationDto> = emptyList()
)
