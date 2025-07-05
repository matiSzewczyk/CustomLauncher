package com.matis.customlauncher.domain.home

import com.matis.customlauncher.core.data.repository.HomeScreenRepository
import javax.inject.Inject

class RemoveApplicationFromHomeScreen @Inject constructor(
    private val repository: HomeScreenRepository
) {

    operator fun invoke(packageName: String) {
        repository.removeApplicationFromHomeScreen(packageName)
    }
}
