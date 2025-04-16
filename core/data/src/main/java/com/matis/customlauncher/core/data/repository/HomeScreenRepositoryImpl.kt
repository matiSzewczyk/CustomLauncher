package com.matis.customlauncher.core.data.repository

import com.matis.core.customlauncher.database.dao.HomeScreenApplicationDao
import com.matis.core.customlauncher.database.model.toDatabase
import com.matis.customlauncher.model.HomeScreenApplicationDto
import javax.inject.Inject

internal class HomeScreenRepositoryImpl @Inject constructor(
    private val dao: HomeScreenApplicationDao
) : HomeScreenRepository{

    override fun insertHomeScreenApplication(application: HomeScreenApplicationDto) {
        dao.insertHomeScreenApplication(application.toDatabase())
    }
}
