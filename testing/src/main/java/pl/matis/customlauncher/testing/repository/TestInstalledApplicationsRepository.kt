package pl.matis.customlauncher.testing.repository

import com.matis.customlauncher.core.data.repository.InstalledApplicationsRepository
import com.matis.customlauncher.model.domain.ApplicationInfoDto
import kotlinx.coroutines.flow.MutableStateFlow
import pl.matis.customlauncher.testing.data.installedApplications

class TestInstalledApplicationsRepository : InstalledApplicationsRepository {

    val applications = MutableStateFlow(installedApplications)

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
