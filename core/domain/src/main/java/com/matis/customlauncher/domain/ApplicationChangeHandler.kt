package com.matis.customlauncher.domain

import com.matis.customlauncher.core.data.repository.InstalledApplicationsRepository
import javax.inject.Inject

class ApplicationChangeHandler @Inject constructor(
    private val repository: InstalledApplicationsRepository
) {

    fun onApplicationAdded(packageName: String) {
        repository.insertApplication(packageName)
    }

    fun onApplicationRemoved(packageName: String) {
        repository.removeApplication(packageName)
    }
}
