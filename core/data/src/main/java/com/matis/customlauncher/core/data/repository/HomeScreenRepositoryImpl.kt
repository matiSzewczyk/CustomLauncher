package com.matis.customlauncher.core.data.repository

import com.matis.core.customlauncher.database.dao.HomeScreenApplicationDao
import com.matis.core.customlauncher.database.model.toDomain
import com.matis.customlauncher.model.ApplicationInfoDto
import com.matis.customlauncher.model.HomeScreenApplicationDto
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class HomeScreenRepositoryImpl @Inject constructor(
    private val dao: HomeScreenApplicationDao
) : HomeScreenRepository {

    override fun insertHomeScreenApplication(application: ApplicationInfoDto) {
        dao.insertHomeScreenApplication(application)
    }

    override fun fetchHomeScreenApplications(): Flow<List<HomeScreenApplicationDto>> =
        dao.fetchAllHomeScreenApplications()
            .map { applications -> applications.map { it.toDomain() } }

    override fun removeApplicationFromHomeScreen(packageName: String) {
        dao.removeApplicationFromHomeScreen(packageName)
    }
}
