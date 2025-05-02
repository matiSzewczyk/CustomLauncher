package com.matis.customlauncher.applications

import com.matis.customlauncher.model.domain.ApplicationInfoDto

interface ApplicationsApi {
    fun isDefaultHomeApplication(): Boolean
    fun getInstalledPackages(): List<ApplicationInfoDto>
    fun getPackageInfo(packageName: String): ApplicationInfoDto?
}
