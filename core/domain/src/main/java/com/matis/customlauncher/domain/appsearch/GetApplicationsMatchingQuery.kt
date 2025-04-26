package com.matis.customlauncher.domain.appsearch

import com.matis.customlauncher.core.data.repository.InstalledApplicationsRepository
import com.matis.customlauncher.model.ApplicationInfoDto
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetApplicationsMatchingQuery @Inject constructor(
    private val repository: InstalledApplicationsRepository
) {

    operator fun invoke(query: String): Flow<List<ApplicationInfoDto>> =
        repository.fetchApplications()
            .map { apps -> apps.filterLabelContaining(query).sortAlphabetically() }

    private fun List<ApplicationInfoDto>.filterLabelContaining(query: String): List<ApplicationInfoDto> =
        filter { it.label.contains(query, ignoreCase = true) }

    private fun List<ApplicationInfoDto>.sortAlphabetically(): List<ApplicationInfoDto> =
        sortedBy { it.label }
}
