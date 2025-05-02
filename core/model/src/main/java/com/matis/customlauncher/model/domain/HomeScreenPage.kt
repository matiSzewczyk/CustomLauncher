package com.matis.customlauncher.model.domain

data class HomeScreenPageDto(
    val position: Int,
    val applications: List<HomeScreenApplicationDto>,
    val folders: List<HomeScreenFolderDto>
)
