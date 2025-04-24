package com.matis.customlauncher.core.data.repository

import com.matis.customlauncher.model.ApplicationInfoDto

interface HomeScreenRepository {
    fun insertHomeScreenApplication(application: ApplicationInfoDto)
}
