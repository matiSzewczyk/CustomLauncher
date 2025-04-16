package com.matis.core.customlauncher.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.matis.customlauncher.model.HomeScreenApplicationDto

/**
 * [position_index] is the index of the application on the home screen.
 * Not row/column is used as I plan on adding customisation to
 * the layout structure.
 */
@Entity(tableName = "home_screen_application")
data class HomeScreenApplicationEntity(
    @PrimaryKey
    @ColumnInfo(name = "package_name") val packageName: String,
    @ColumnInfo(name = "label") val label: String,
    @ColumnInfo(name = "position_index") val position: Int
)

fun HomeScreenApplicationDto.toDatabase(): HomeScreenApplicationEntity =
    HomeScreenApplicationEntity(
        packageName = packageName,
        label = label,
        position = position
    )

fun HomeScreenApplicationEntity.toDomain(): HomeScreenApplicationDto =
    HomeScreenApplicationDto(
        packageName = packageName,
        label = label,
        position = position
    )
