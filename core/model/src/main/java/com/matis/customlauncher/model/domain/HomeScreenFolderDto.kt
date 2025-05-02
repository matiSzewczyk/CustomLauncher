package com.matis.customlauncher.model.domain

data class HomeScreenFolderDto(
    val label: String,
    val position: Int,
    val applications: List<HomeScreenApplicationDto>
    // TODO: most likely will need an app cap and layout type
)
