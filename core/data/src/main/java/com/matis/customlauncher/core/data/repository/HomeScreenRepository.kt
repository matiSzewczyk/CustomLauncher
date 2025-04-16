package com.matis.customlauncher.core.data.repository

import com.matis.customlauncher.model.HomeScreenApplicationDto

interface HomeScreenRepository {
    fun insertHomeScreenApplication(application: HomeScreenApplicationDto)
}
