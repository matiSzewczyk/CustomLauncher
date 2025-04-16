package com.matis.core.customlauncher.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.matis.core.customlauncher.database.dao.HomeScreenApplicationDao
import com.matis.core.customlauncher.database.model.HomeScreenApplicationEntity

@Database(
    entities = [HomeScreenApplicationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LauncherRoomDatabase : RoomDatabase() {
    abstract fun getHomeScreenApplicationDao(): HomeScreenApplicationDao
}
