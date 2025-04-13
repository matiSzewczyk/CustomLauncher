package com.matis.customlauncher.device

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_MAIN
import android.content.Intent.CATEGORY_LAUNCHER
import android.content.pm.PackageManager.MATCH_DEFAULT_ONLY
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import com.matis.customlauncher.domain.data.model.PackageDto
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PackagesApi @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val _isDefaultHomeApp = MutableStateFlow(false)
    val isDefaultHomeApp get() = _isDefaultHomeApp.asStateFlow()

    fun checkIfIsDefaultHomeApp() {
        val resolveInfo = Intent(ACTION_MAIN)
            .apply { addCategory(Intent.CATEGORY_HOME) }
            .let { intent -> context.packageManager.resolveActivity(intent, MATCH_DEFAULT_ONLY) }

        _isDefaultHomeApp.update { resolveInfo?.activityInfo?.packageName == context.packageName }
    }

    fun getInstalledPackages(): List<PackageDto> =
        queryLauncherActivities().map { it.toPackageDto() }

    fun getPackageInfo(packageName: String): PackageDto? =
        queryLauncherActivities()
            .find { it.activityInfo.packageName == packageName }
            ?.toPackageDto()

    fun getPackageIcon(packageName: String): Drawable =
        context.packageManager.getApplicationIcon(packageName)

    private fun queryLauncherActivities(): List<ResolveInfo> =
        context.packageManager.queryIntentActivities(
            Intent(ACTION_MAIN).apply { addCategory(CATEGORY_LAUNCHER) },
            0
        )

    private fun ResolveInfo.toPackageDto(): PackageDto =
        PackageDto(
            packageName = activityInfo.packageName,
            label = loadLabel(context.packageManager).toString()
        )
}
