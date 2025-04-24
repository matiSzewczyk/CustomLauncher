package com.matis.customlauncher.domain

import com.matis.customlauncher.core.data.repository.HomeScreenRepository
import com.matis.customlauncher.model.ApplicationInfoDto
import javax.inject.Inject

class AddApplicationToHomeScreen @Inject constructor(
    private val repository: HomeScreenRepository
) {

    operator fun invoke(application: ApplicationInfoDto) {
        repository.insertHomeScreenApplication(application)
    }
}
