package com.matis.customlauncher.domain.fakes

import com.matis.customlauncher.core.data.repository.InstalledApplicationsRepository
import com.matis.customlauncher.model.ApplicationInfoDto
import kotlinx.coroutines.flow.MutableStateFlow

class TestInstalledApplicationsRepository : InstalledApplicationsRepository {

    val applications = MutableStateFlow<List<ApplicationInfoDto>>(emptyList())

    override fun insertApplication(packageName: String) {
        val application = ApplicationInfoDto(packageName = packageName, label = "foo")
        applications.value = applications.value + application
    }

    override fun fetchApplications(): MutableStateFlow<List<ApplicationInfoDto>> =
        applications

    override fun removeApplication(packageName: String) {
        applications.value = applications.value.filterNot { it.packageName == packageName }
    }
}
