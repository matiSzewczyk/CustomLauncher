package com.matis.core.customlauncher.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.matis.core.customlauncher.database.model.HomeScreenApplicationEntity
import com.matis.core.customlauncher.database.model.HomeScreenPageEntity
import com.matis.core.customlauncher.database.model.HomeScreenPageRelationData
import com.matis.customlauncher.model.domain.ApplicationInfoDto
import com.matis.customlauncher.model.domain.HomePageLayoutType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

@Dao
abstract class HomeScreenApplicationDao {
    @Insert(onConflict = REPLACE)
    abstract fun insertNewHomeScreenPage(data: HomeScreenPageEntity)

    @Insert(onConflict = REPLACE)
    abstract fun insertHomeScreenApplication(data: HomeScreenApplicationEntity)

    @Query("SELECT * FROM home_screen_page")
    abstract fun fetchAllHomeScreens(): Flow<List<HomeScreenPageRelationData>>

    @Query("SELECT * FROM home_screen_application")
    abstract fun fetchAllHomeScreenApplications(): Flow<List<HomeScreenApplicationEntity>>

    @Query("DELETE FROM home_screen_application WHERE package_name = :packageName")
    abstract fun removeApplicationFromHomeScreen(packageName: String)

    @Query(
        """
            SELECT COALESCE(
                (SELECT 0
                 WHERE NOT EXISTS (SELECT 1 FROM home_screen_application WHERE position = 0)),
                (SELECT MIN(t1.position + 1)
                 FROM home_screen_application t1
                 LEFT JOIN home_screen_application t2
                 ON t1.position + 1 = t2.position
                 WHERE t2.position IS NULL), 
                0
            ) AS first_available_position        
        """
    )
    abstract fun fetchFirstAvailablePosition(): Int

    @Transaction
    open suspend fun insertHomeScreenApplication(
        application: ApplicationInfoDto,
        currentLayout: HomePageLayoutType
    ) {
        val maxAppsPerPage = currentLayout.appCap
        val homeScreens = fetchAllHomeScreens().first().takeIf { it.isNotEmpty() }

        homeScreens?.forEach { homeScreen ->
            val takenPositionsByApps = homeScreen.applications.map { it.position }
            val takenPositionsByFolders = homeScreen.folders.map { it.folder.position }

            val allTakenPositions = takenPositionsByApps + takenPositionsByFolders

            val firstAvailablePosition = (0 until maxAppsPerPage)
                .firstOrNull { it !in allTakenPositions }

            if (firstAvailablePosition != null) {
                val newApplication = HomeScreenApplicationEntity(
                    packageName = application.packageName,
                    label = application.label,
                    homeScreenPageIndex = homeScreen.homeScreenPage.pageIndex,
                    folderPosition = null,
                    folderHomeScreenPageIndex = null,
                    position = firstAvailablePosition
                )
                insertHomeScreenApplication(newApplication)
            }
        } ?: run {
            val newApplication = HomeScreenApplicationEntity(
                packageName = application.packageName,
                label = application.label,
                homeScreenPageIndex = 0,
                folderPosition = null,
                folderHomeScreenPageIndex = null,
                position = 0
            )

            insertNewHomeScreenPage(HomeScreenPageEntity(pageIndex = 0))
            insertHomeScreenApplication(newApplication)
        }
    }
}
