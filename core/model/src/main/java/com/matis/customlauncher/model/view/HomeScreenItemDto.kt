package com.matis.customlauncher.model.view

import androidx.compose.runtime.Stable
import com.matis.customlauncher.model.domain.HomeScreenApplicationDto
import com.matis.customlauncher.model.domain.HomeScreenFolderDto

sealed class HomeScreenItemDto(
    open val position: Int
) {
    @Stable
    data class Application(
        override val position: Int,
        val packageName: String,
        val label: String
    ) : HomeScreenItemDto(position)

    data class Folder(
        override val position: Int,
        val label: String,
        val applications: List<Application>
    ) : HomeScreenItemDto(position)

    data class Empty(
        override val position: Int
    ) : HomeScreenItemDto(position)
}

fun HomeScreenApplicationDto.toView(): HomeScreenItemDto.Application =
    HomeScreenItemDto.Application(
        packageName = packageName,
        label = label,
        position = position
    )

fun HomeScreenFolderDto.toView(): HomeScreenItemDto.Folder =
    HomeScreenItemDto.Folder(
        label = label,
        position = position,
        applications = applications.map { it.toView() }
    )
