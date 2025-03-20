package com.matis.customlauncher.domain

import com.matis.customlauncher.device.PackageManagerWrapper
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAllApplications @Inject constructor(
    private val wrapper: PackageManagerWrapper
) {

    suspend operator fun invoke(): List<String> =
        withContext(Dispatchers.IO) { wrapper.getAllInstalledApplications() }
}
