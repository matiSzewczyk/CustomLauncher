package com.matis.customlauncher.ui.appsearch.data.model

import com.matis.customlauncher.model.view.ApplicationInfoViewDto

data class UiState(
    val query: String = "",
    val applications: List<ApplicationInfoViewDto> = emptyList()
)
