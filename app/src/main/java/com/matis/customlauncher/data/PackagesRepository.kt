package com.matis.customlauncher.data

import com.matis.customlauncher.device.PackageManagerWrapper
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PackagesRepository @Inject constructor(
    private val packageManagerWrapper: PackageManagerWrapper
) {

    var applications = MutableStateFlow<List<String>>(emptyList())
        private set

    init {
        CoroutineScope(Dispatchers.IO).launch {
            packageManagerWrapper.getAllInstalledApplications()
                .let { applications.value = it }
        }
    }

    fun insertApplication(packageName: String) {
        applications.value = applications.value + packageName
    }

    fun removeApplication(packageName: String) {
        applications.value = applications.value - packageName
    }
}
