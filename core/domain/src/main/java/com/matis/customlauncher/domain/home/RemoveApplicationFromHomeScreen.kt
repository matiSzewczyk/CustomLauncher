package com.matis.customlauncher.domain.home

import com.matis.customlauncher.core.data.repository.HomeScreenRepository
import javax.inject.Inject

class RemoveApplicationFromHomeScreen @Inject constructor(
    private val homeScreenRepository: HomeScreenRepository
) {

    operator fun invoke(packageName: String) {
        homeScreenRepository.removeApplicationFromHomeScreen(packageName)
    }
}
