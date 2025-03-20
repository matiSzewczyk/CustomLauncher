package com.matis.customlauncher.ui.appsearch

data class UiState(
    val query: String = "",
    val searchResults: List<String> = emptyList()
)
