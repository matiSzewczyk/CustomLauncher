package com.matis.customlauncher.domain.appsearch

import com.matis.customlauncher.core.data.repository.HomeScreenRepository
import com.matis.customlauncher.model.domain.ApplicationInfoDto
import javax.inject.Inject

class AddApplicationToHomeScreen @Inject constructor(
    private val repository: HomeScreenRepository
) {

    suspend operator fun invoke(application: ApplicationInfoDto) {
        repository.insertHomeScreenApplication(application)
    }
}
