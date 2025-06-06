package com.matis.customlauncher.device.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.matis.customlauncher.domain.ApplicationChangeHandler
import javax.inject.Inject

class PackageUninstallReceiver @Inject constructor(
    private val handler: ApplicationChangeHandler
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val packageName = intent?.data?.encodedSchemeSpecificPart ?: return

        handler.onApplicationRemoved(packageName)
        Log.d(TAG, "Application uninstalled: $packageName")
    }

    fun intentFilter(): IntentFilter =
        IntentFilter()
            .apply {
                addAction(Intent.ACTION_PACKAGE_REMOVED)
                addDataScheme("package")
            }

    companion object {
        private const val TAG = "PackageUninstallReceiver"
    }
}
