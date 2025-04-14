package com.matis.customlauncher.ui.appsearch

import com.matis.customlauncher.domain.data.model.PackageInfoDto

data class UiState(
    val query: String = "",
    val applications: List<PackageInfoDto> = emptyList()
)
