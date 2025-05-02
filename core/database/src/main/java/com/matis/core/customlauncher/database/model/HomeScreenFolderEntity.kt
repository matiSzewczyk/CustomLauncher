package com.matis.core.customlauncher.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "home_screen_folder",
    primaryKeys = ["home_screen_page_index", "position"],
    foreignKeys = [ForeignKey(
        entity = HomeScreenPageEntity::class,
        parentColumns = ["page_index"],
        childColumns = ["home_screen_page_index"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class HomeScreenFolderEntity(
    @ColumnInfo(name = "home_screen_page_index") val homeScreenPageIndex: Int,
    @ColumnInfo(name = "position") val position: Int,
    @ColumnInfo(name = "label") val label: String
)
