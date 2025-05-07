package com.matis.customlauncher.domain.home

import com.matis.customlauncher.core.data.repository.HomeScreenRepository
import com.matis.customlauncher.model.domain.HomeScreenDto
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetHomeScreens @Inject constructor(
    private val repository: HomeScreenRepository
) {

    operator fun invoke(): Flow<HomeScreenDto> =
        repository.fetchHomeScreens()
}
