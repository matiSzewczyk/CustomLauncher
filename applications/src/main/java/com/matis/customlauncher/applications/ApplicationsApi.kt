package com.matis.customlauncher.applications

import com.matis.customlauncher.model.ApplicationInfoDto

interface ApplicationsApi {
    fun evaluateHomeApplicationStatus()
    fun getInstalledPackages(): List<ApplicationInfoDto>
    fun getPackageInfo(packageName: String): ApplicationInfoDto?
}
