package com.matis.customlauncher.data.dal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.matis.customlauncher.data.LauncherRoomDatabase
import com.matis.customlauncher.data.model.HomeScreenApplicationEntity
import com.matis.customlauncher.data.model.toDatabase
import com.matis.customlauncher.data.model.toDomain
import com.matis.customlauncher.domain.data.model.HomeScreenApplicationDto
import javax.inject.Inject

class HomeScreenDao @Inject constructor(
    private val database: LauncherRoomDatabase
) {

    fun insertHomeScreenApplication(application: HomeScreenApplicationDto) {
        database.getHomeScreenDao().insertHomeScreenApplication(application.toDatabase())
    }

    fun fetchAllHomeScreenApplications(): List<HomeScreenApplicationDto> =
        database.getHomeScreenDao().fetchAllHomeScreenApplications()
            .map { it.toDomain() }

    @Dao
    interface RoomDao {
        @Insert(onConflict = REPLACE)
        fun insertHomeScreenApplication(data: HomeScreenApplicationEntity)

        @Query("SELECT * FROM home_screen_application")
        fun fetchAllHomeScreenApplications(): List<HomeScreenApplicationEntity>
    }
}
