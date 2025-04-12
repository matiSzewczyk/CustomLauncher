package com.matis.customlauncher.domain

import com.matis.customlauncher.data.PackagesRepository
import com.matis.customlauncher.domain.data.model.PackageDto
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

    fun getApplications(filter: String): Flow<List<PackageDto>> =
        repository.applications
            .map { apps -> apps.filter { it.packageName.contains(filter) } }
}
