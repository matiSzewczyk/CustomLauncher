package com.matis.customlauncher.core.data.repository

import com.matis.customlauncher.model.ApplicationInfoDto
import com.matis.customlauncher.model.HomeScreenApplicationDto
import kotlinx.coroutines.flow.Flow

interface HomeScreenRepository {
    fun insertHomeScreenApplication(application: ApplicationInfoDto)
    fun fetchHomeScreenApplications(): Flow<List<HomeScreenApplicationDto>>
    fun removeApplicationFromHomeScreen(packageName: String)
}
