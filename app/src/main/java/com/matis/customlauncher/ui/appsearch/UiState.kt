package com.matis.customlauncher.ui.appsearch

import com.matis.customlauncher.domain.data.model.PackageDto

data class UiState(
    val query: String = "",
    val applications: List<PackageDto> = emptyList()
)
