package com.matis.customlauncher.domain.home

import com.matis.customlauncher.core.data.repository.HomeScreenRepository
import javax.inject.Inject

class RemoveApplicationsPage @Inject constructor(
    private val repository: HomeScreenRepository
) {

    suspend operator fun invoke(pageIndex: Int) {
        repository.removeApplicationsPage(pageIndex)
    }
}
