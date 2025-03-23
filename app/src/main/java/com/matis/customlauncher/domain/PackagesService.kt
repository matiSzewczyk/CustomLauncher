package com.matis.customlauncher.domain

import com.matis.customlauncher.data.PackagesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PackagesService @Inject constructor(
    private val repository: PackagesRepository
) {

    fun onApplicationAdded(packageName: String) {
        repository.insertApplication(packageName)
    }

    fun getApplications(filter: String): Flow<List<String>> =
        repository.applications
            .map { apps -> apps.filter { it.contains(filter) } }
}
