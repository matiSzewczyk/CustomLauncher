package com.matis.customlauncher.applications

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_MAIN
import android.content.Intent.CATEGORY_LAUNCHER
import android.content.pm.PackageManager.MATCH_DEFAULT_ONLY
import android.content.pm.ResolveInfo
import com.matis.customlauncher.model.ApplicationInfoDto
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class ApplicationsApiImpl @Inject constructor(
    private val context: Context
) : ApplicationsApi {

    private val _isDefaultHomeApp = MutableStateFlow(false)
    val isDefaultHomeApp get() = _isDefaultHomeApp.asStateFlow()

    override fun evaluateHomeApplicationStatus() {
        val resolveInfo = Intent(ACTION_MAIN)
            .apply { addCategory(Intent.CATEGORY_HOME) }
            .let { intent -> context.packageManager.resolveActivity(intent, MATCH_DEFAULT_ONLY) }

        _isDefaultHomeApp.update { resolveInfo?.activityInfo?.packageName == context.packageName }
    }

    override fun getInstalledPackages(): List<ApplicationInfoDto> =
        queryLauncherActivities().map { it.toDomain() }

    override fun getPackageInfo(packageName: String): ApplicationInfoDto? =
        queryLauncherActivities()
            .find { it.activityInfo.packageName == packageName }
            ?.toDomain()

    private fun queryLauncherActivities(): List<ResolveInfo> =
        context.packageManager.queryIntentActivities(
            Intent(ACTION_MAIN).apply { addCategory(CATEGORY_LAUNCHER) },
            0
        )

    private fun ResolveInfo.toDomain(): ApplicationInfoDto =
        ApplicationInfoDto(
            packageName = activityInfo.packageName,
            label = loadLabel(context.packageManager).toString()
        )
}
