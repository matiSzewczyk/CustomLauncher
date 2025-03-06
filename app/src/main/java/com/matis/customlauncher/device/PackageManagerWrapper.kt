package com.matis.customlauncher.device

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.MATCH_DEFAULT_ONLY
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PackageManagerWrapper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val _isDefaultLauncherApplication = MutableStateFlow(false)
    val isDefaultLauncherApplication get() = _isDefaultLauncherApplication.asStateFlow()

    fun forceCheck() {
        val intent = Intent(Intent.ACTION_MAIN).apply { addCategory(Intent.CATEGORY_HOME) }
        val resolveInfo = context.packageManager.resolveActivity(intent, MATCH_DEFAULT_ONLY)
        _isDefaultLauncherApplication.update { resolveInfo?.activityInfo?.packageName == context.packageName }
    }
}
