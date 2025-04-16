package com.matis.customlauncher.core.data.repository

import com.matis.core.customlauncher.database.dao.HomeScreenApplicationDao
import com.matis.customlauncher.applications.ApplicationsApi
import com.matis.customlauncher.model.ApplicationInfoDto
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

internal class InstalledApplicationsRepositoryImpl @Inject constructor(
    private val applicationsApi: ApplicationsApi,
    private val homeScreenApplicationDao: HomeScreenApplicationDao
) : InstalledApplicationsRepository{

    var applications = MutableStateFlow<List<ApplicationInfoDto>>(emptyList())
        private set

    init {
        CoroutineScope(Dispatchers.IO).launch {
            applications.value = applicationsApi.getInstalledPackages()
        }
    }

    override fun insertApplication(packageName: String) {
        applicationsApi.getPackageInfo(packageName)
            ?.let { applications.value = applications.value + it }
    }

    override fun removeApplication(packageName: String) {
        applications.value = applications.value.filterNot { it.packageName == packageName }
    }
}
