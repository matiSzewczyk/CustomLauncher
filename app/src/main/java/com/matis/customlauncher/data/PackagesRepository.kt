package com.matis.customlauncher.data

import com.matis.customlauncher.device.PackagesApi
import com.matis.customlauncher.domain.data.model.PackageInfoDto
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PackagesRepository @Inject constructor(
    private val packagesApi: PackagesApi
) {

    var applications = MutableStateFlow<List<PackageInfoDto>>(emptyList())
        private set

    init {
        CoroutineScope(Dispatchers.IO).launch {
            applications.value = packagesApi.getInstalledPackages()
        }
    }

    fun insertApplication(packageName: String) {
        packagesApi.getPackageInfo(packageName)
            ?.let { applications.value = applications.value + it }
    }

    fun removeApplication(packageName: String) {
        applications.value = applications.value.filterNot { it.packageName == packageName }
    }
}
