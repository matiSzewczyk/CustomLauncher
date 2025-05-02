package com.matis.core.customlauncher.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.matis.customlauncher.model.domain.HomeScreenApplicationDto
import com.matis.customlauncher.model.domain.HomeScreenFolderDto
import com.matis.customlauncher.model.domain.HomeScreenPageDto

data class HomeScreenPageRelationData(
    @Embedded val homeScreenPage: HomeScreenPageEntity,
    @Relation(
        parentColumn = "page_index",
        entityColumn = "home_screen_page_index",
        entity = HomeScreenFolderEntity::class
    ) val folders: List<HomeScreenFolderRelationData>,
    @Relation(
        parentColumn = "page_index",
        entityColumn = "home_screen_page_index",
        entity = HomeScreenApplicationEntity::class
    ) val applications: List<HomeScreenApplicationEntity>
)

data class HomeScreenFolderRelationData(
    @Embedded val folder: HomeScreenFolderEntity,
    @Relation(
        parentColumn = "position",
        entityColumn = "folder_position",
        entity = HomeScreenApplicationEntity::class
    ) val applications: List<HomeScreenApplicationEntity>
)

fun HomeScreenPageRelationData.toDomain(): HomeScreenPageDto =
    HomeScreenPageDto(
        position = homeScreenPage.pageIndex,
        applications = applications.map { it.toDomain() },
        folders = folders.map { it.toDomain() }
    )

fun HomeScreenApplicationEntity.toDomain(): HomeScreenApplicationDto =
    HomeScreenApplicationDto(
        packageName = packageName,
        label = label,
        position = position
    )

fun HomeScreenFolderRelationData.toDomain(): HomeScreenFolderDto =
    HomeScreenFolderDto(
        label = folder.label,
        position = folder.position,
        applications = applications.map { it.toDomain() }
    )
