package com.matis.core.customlauncher.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.matis.core.customlauncher.database.model.HomeScreenApplicationEntity
import com.matis.customlauncher.model.ApplicationInfoDto
import kotlinx.coroutines.flow.Flow

@Dao
abstract class HomeScreenApplicationDao {
    @Insert(onConflict = REPLACE)
    abstract fun insertHomeScreenApplication(data: HomeScreenApplicationEntity)

    @Query("SELECT * FROM home_screen_application")
    abstract fun fetchAllHomeScreenApplications(): Flow<List<HomeScreenApplicationEntity>>

    @Query(
        """
    SELECT COALESCE(MIN(next_index), 0) AS first_available_position
    FROM (
        SELECT t1.position + 1 AS next_index
        FROM home_screen_application t1
        LEFT JOIN home_screen_application t2
            ON t1.position + 1 = t2.position
        WHERE t2.position IS NULL
    )
"""
    )
    abstract fun fetchFirstAvailablePosition(): Int

    @Transaction
    open fun insertHomeScreenApplication(application: ApplicationInfoDto) {
        val availablePosition = fetchFirstAvailablePosition()

        val entity = HomeScreenApplicationEntity(
            packageName = application.packageName,
            label = application.label,
            position = availablePosition
        )
        insertHomeScreenApplication(entity)
    }
}
