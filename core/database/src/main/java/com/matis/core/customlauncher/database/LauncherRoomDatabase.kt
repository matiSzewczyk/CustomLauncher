package com.matis.core.customlauncher.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.matis.core.customlauncher.database.dao.HomeScreenApplicationDao
import com.matis.core.customlauncher.database.model.HomeScreenApplicationEntity
import com.matis.core.customlauncher.database.model.HomeScreenFolderEntity
import com.matis.core.customlauncher.database.model.HomeScreenPageEntity

@Database(
    entities = [
        HomeScreenPageEntity::class,
        HomeScreenFolderEntity::class,
        HomeScreenApplicationEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class LauncherRoomDatabase : RoomDatabase() {
    abstract fun getHomeScreenApplicationDao(): HomeScreenApplicationDao
}
