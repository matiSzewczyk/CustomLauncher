package com.matis.customlauncher.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.matis.customlauncher.data.dal.HomeScreenDao
import com.matis.customlauncher.data.model.HomeScreenApplicationEntity

@Database(
    entities = [HomeScreenApplicationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LauncherRoomDatabase : RoomDatabase() {
    abstract fun getHomeScreenDao(): HomeScreenDao.RoomDao
}
