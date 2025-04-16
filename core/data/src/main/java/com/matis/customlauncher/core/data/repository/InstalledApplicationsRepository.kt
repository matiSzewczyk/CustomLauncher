package com.matis.customlauncher.core.data.repository

interface InstalledApplicationsRepository {
    fun insertApplication(packageName: String)
    fun removeApplication(packageName: String)
}
