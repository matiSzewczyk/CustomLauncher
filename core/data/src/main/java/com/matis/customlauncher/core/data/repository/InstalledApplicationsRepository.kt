package com.matis.customlauncher.core.data.repository

import com.matis.customlauncher.model.ApplicationInfoDto
import kotlinx.coroutines.flow.MutableStateFlow

interface InstalledApplicationsRepository {
    fun insertApplication(packageName: String)
    fun getApplications(): MutableStateFlow<List<ApplicationInfoDto>>
    fun removeApplication(packageName: String)
}
