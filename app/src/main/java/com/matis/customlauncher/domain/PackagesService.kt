package com.matis.customlauncher.domain

import com.matis.customlauncher.data.dal.PackagesRepository
import com.matis.customlauncher.domain.data.model.HomeScreenApplicationDto
import com.matis.customlauncher.domain.data.model.PackageInfoDto
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PackagesService @Inject constructor(
    private val repository: PackagesRepository
) {

    fun onApplicationAdded(packageName: String) {
        repository.insertApplication(packageName)
    }

    fun onApplicationRemoved(packageName: String) {
        repository.removeApplication(packageName)
    }

    fun getApplications(query: String): Flow<List<PackageInfoDto>> =
        repository.applications
            .map { apps -> apps.filter { it.label.contains(query, ignoreCase = true) } }

    fun addToHomeScreen(application: HomeScreenApplicationDto) {
        repository.insertNewHomeScreenApplication(application)
    }
}
