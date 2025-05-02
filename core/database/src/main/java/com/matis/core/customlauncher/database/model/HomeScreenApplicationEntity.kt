package com.matis.core.customlauncher.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "home_screen_application",
    foreignKeys = [
        ForeignKey(
            entity = HomeScreenPageEntity::class,
            parentColumns = ["page_index"],
            childColumns = ["home_screen_page_index"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = HomeScreenFolderEntity::class,
            parentColumns = ["home_screen_page_index", "position"],
            childColumns = ["home_screen_page_index", "folder_position"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HomeScreenApplicationEntity(
    @PrimaryKey
    @ColumnInfo(name = "package_name") val packageName: String,
    @ColumnInfo(name = "label") val label: String,
    @ColumnInfo(name = "home_screen_page_index") val homeScreenPageIndex: Int?,
    @ColumnInfo(name = "folder_position") val folderPosition: Int?,
    @ColumnInfo(name = "folder_home_screen_page_index") val folderHomeScreenPageIndex: Int?,
    @ColumnInfo(name = "position") val position: Int
)
