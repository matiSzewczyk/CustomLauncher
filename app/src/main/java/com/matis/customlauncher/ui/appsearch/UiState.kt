package com.matis.customlauncher.ui.appsearch

import com.matis.customlauncher.model.ApplicationInfoDto

data class UiState(
    val query: String = "",
    val applications: List<ApplicationInfoDto> = emptyList()
)
