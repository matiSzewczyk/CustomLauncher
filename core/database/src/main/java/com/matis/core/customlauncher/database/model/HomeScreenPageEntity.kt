package com.matis.core.customlauncher.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "home_screen_page")
data class HomeScreenPageEntity(
    @PrimaryKey
    @ColumnInfo(name = "page_index") val pageIndex: Int
)
