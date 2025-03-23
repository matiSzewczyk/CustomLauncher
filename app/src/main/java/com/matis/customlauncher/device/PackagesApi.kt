package com.matis.customlauncher.device

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_MAIN
import android.content.Intent.CATEGORY_LAUNCHER
import android.content.pm.PackageManager.MATCH_DEFAULT_ONLY
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PackagesApi @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val _isDefaultLauncherApplication = MutableStateFlow(false)
    val isDefaultLauncherApplication get() = _isDefaultLauncherApplication.asStateFlow()

    fun checkIfIsDefaultHomeApp() {
        val resolveInfo = Intent(ACTION_MAIN)
            .apply { addCategory(Intent.CATEGORY_HOME) }
            .let { intent -> context.packageManager.resolveActivity(intent, MATCH_DEFAULT_ONLY) }

        _isDefaultLauncherApplication.update { resolveInfo?.activityInfo?.packageName == context.packageName }
    }

    fun getAllInstalledApplications(): List<String> =
        Intent(ACTION_MAIN, null)
            .apply { addCategory(CATEGORY_LAUNCHER) }
            .let { intent -> context.packageManager.queryIntentActivities(intent, 0) }
            .map { resolveInfo -> resolveInfo.activityInfo.packageName }
}
