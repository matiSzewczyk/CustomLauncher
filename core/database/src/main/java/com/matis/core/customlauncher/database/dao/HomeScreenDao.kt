package com.matis.core.customlauncher.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.matis.core.customlauncher.database.model.HomeScreenApplicationEntity

@Dao
interface HomeScreenApplicationDao {
    @Insert(onConflict = REPLACE)
    fun insertHomeScreenApplication(data: HomeScreenApplicationEntity)

    @Query("SELECT * FROM home_screen_application")
    fun fetchAllHomeScreenApplications(): List<HomeScreenApplicationEntity>
}
