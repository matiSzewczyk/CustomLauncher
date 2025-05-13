package com.matis.customlauncher.core.data.repository

import com.matis.customlauncher.model.domain.ApplicationInfoDto
import com.matis.customlauncher.model.domain.HomePageLayoutType
import com.matis.customlauncher.model.domain.HomeScreenApplicationDto
import com.matis.customlauncher.model.domain.HomeScreenDto
import kotlinx.coroutines.flow.Flow

interface HomeScreenRepository {
    suspend fun insertHomeScreenApplication(application: ApplicationInfoDto)
    suspend fun reconfigureHomeScreenLayout(newLayout: HomePageLayoutType)
    suspend fun fetchHomeScreenLayout(): HomePageLayoutType
    suspend fun insertNewApplicationsPage()
    suspend fun removeApplicationsPage(pageIndex: Int)
    fun fetchHomeScreens(): Flow<HomeScreenDto>
    fun fetchHomeScreenApplications(): Flow<List<HomeScreenApplicationDto>>
    fun removeApplicationFromHomeScreen(packageName: String)
}
