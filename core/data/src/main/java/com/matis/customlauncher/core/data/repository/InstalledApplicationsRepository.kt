package com.matis.customlauncher.core.data.repository

import com.matis.customlauncher.model.domain.ApplicationInfoDto
import kotlinx.coroutines.flow.MutableStateFlow

interface InstalledApplicationsRepository {
    fun insertApplication(packageName: String)
    fun fetchApplications(): MutableStateFlow<List<ApplicationInfoDto>>
    fun removeApplication(packageName: String)
}
