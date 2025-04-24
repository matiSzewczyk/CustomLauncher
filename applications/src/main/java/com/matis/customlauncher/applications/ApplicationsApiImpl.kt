package com.matis.customlauncher.applications

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_MAIN
import android.content.Intent.CATEGORY_LAUNCHER
import android.content.pm.PackageManager.MATCH_DEFAULT_ONLY
import android.content.pm.ResolveInfo
import com.matis.customlauncher.model.ApplicationInfoDto
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class ApplicationsApiImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ApplicationsApi {

    override fun isDefaultHomeApplication(): Boolean =
        Intent(ACTION_MAIN)
            .apply { addCategory(Intent.CATEGORY_HOME) }
            .let { intent -> context.packageManager.resolveActivity(intent, MATCH_DEFAULT_ONLY) }
            ?.run { activityInfo?.packageName == context.packageName } == true

    override fun getInstalledPackages(): List<ApplicationInfoDto> =
        queryLauncherActivities().map { it.toDomain() }

    override fun getPackageInfo(packageName: String): ApplicationInfoDto? =
        queryLauncherActivities()
            .find { it.activityInfo.packageName == packageName }
            ?.toDomain()

    private fun queryLauncherActivities(): List<ResolveInfo> {
        val intent = Intent(ACTION_MAIN).apply { addCategory(CATEGORY_LAUNCHER) }

        return context.packageManager.queryIntentActivities(intent, 0)
    }

    private fun ResolveInfo.toDomain(): ApplicationInfoDto =
        ApplicationInfoDto(
            packageName = activityInfo.packageName,
            label = loadLabel(context.packageManager).toString()
        )
}
