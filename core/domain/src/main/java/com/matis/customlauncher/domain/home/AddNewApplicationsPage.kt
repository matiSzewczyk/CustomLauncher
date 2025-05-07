package com.matis.customlauncher.domain.home

import com.matis.customlauncher.core.data.repository.HomeScreenRepository
import javax.inject.Inject

class AddNewApplicationsPage @Inject constructor(
    private val homeScreenRepository: HomeScreenRepository
) {

    suspend operator fun invoke() {
        homeScreenRepository.insertNewApplicationsPage()
    }
}
