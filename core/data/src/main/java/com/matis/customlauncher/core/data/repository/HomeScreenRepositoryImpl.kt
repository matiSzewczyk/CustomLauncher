package com.matis.customlauncher.core.data.repository

import com.matis.core.customlauncher.database.dao.HomeScreenApplicationDao
import com.matis.customlauncher.model.ApplicationInfoDto
import javax.inject.Inject

internal class HomeScreenRepositoryImpl @Inject constructor(
    private val dao: HomeScreenApplicationDao
) : HomeScreenRepository {

    override fun insertHomeScreenApplication(application: ApplicationInfoDto) {
        dao.insertHomeScreenApplication(application)
    }
}
