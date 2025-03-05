package com.matis.customlauncher.device

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.MATCH_DEFAULT_ONLY
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PackageManagerWrapper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun isDefaultLauncherApplication(): Flow<Boolean> =
        flow {
            val intent = Intent(Intent.ACTION_MAIN).apply { addCategory(Intent.CATEGORY_HOME) }
            val resolveInfo = context.packageManager.resolveActivity(intent, MATCH_DEFAULT_ONLY)
            emit(resolveInfo?.activityInfo?.packageName == context.packageName)
        }
}
