package com.matis.customlauncher.domain

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
            .map { apps ->
                apps.filter { it.label.contains(query, ignoreCase = true) }
            }
}
