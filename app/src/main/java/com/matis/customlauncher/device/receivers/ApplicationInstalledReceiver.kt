package com.matis.customlauncher.device.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.matis.customlauncher.domain.PackagesService
import javax.inject.Inject

class ApplicationInstalledReceiver @Inject constructor(
    private val packagesService: PackagesService
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val packageName = intent?.data?.encodedSchemeSpecificPart ?: return

        packagesService.onApplicationAdded(packageName)
        Log.d(TAG, "Application installed: $packageName")
    }

    fun intentFilter(): IntentFilter =
        IntentFilter()
            .apply {
                addAction(Intent.ACTION_PACKAGE_ADDED)
                addDataScheme("package")
            }

    companion object {
        private const val TAG = "ApplicationInstalledReceiver"
    }
}
