package pl.matis.customlauncher.testing.repository

import com.matis.customlauncher.core.data.repository.InstalledApplicationsRepository
import com.matis.customlauncher.model.domain.ApplicationInfoDto
import kotlinx.coroutines.flow.MutableStateFlow

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

    companion object {
        val installedApplications: List<ApplicationInfoDto> = buildList {
            add(ApplicationInfoDto(packageName = "com.example", label = "Example"))
            add(ApplicationInfoDto(packageName = "com.foo", label = "Foo"))
            add(ApplicationInfoDto(packageName = "com.bar", label = "Bar"))
            add(ApplicationInfoDto(packageName = "com.baz", label = "Baz"))
        }
    }
}
