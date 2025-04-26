package com.matis.customlauncher.domain

import com.matis.customlauncher.core.data.repository.HomeScreenRepository
import com.matis.customlauncher.model.HomeScreenApplicationDto
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetHomeScreenApplications @Inject constructor(
    private val repository: HomeScreenRepository
) {

    operator fun invoke(): Flow<List<HomeScreenApplicationDto>> =
        repository.fetchHomeScreenApplications()
}
