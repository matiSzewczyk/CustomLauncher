package com.matis.customlauncher.domain.home

import com.matis.customlauncher.core.data.repository.HomeScreenRepository
import com.matis.customlauncher.model.domain.HomeScreenDto
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart

class GetHomeScreens @Inject constructor(
    private val repository: HomeScreenRepository,
    private val addNewApplicationsPage: AddNewApplicationsPage
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<HomeScreenDto> =
        repository.fetchHomeScreens()
            .onStart { if (!hasHomeScreenPages()) addNewApplicationsPage() }

    private suspend fun hasHomeScreenPages(): Boolean =
        repository.fetchHomeScreens().first().pages.isNotEmpty()
}
