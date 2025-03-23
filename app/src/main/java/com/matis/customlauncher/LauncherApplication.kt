package com.matis.customlauncher

import android.app.Application
import android.content.BroadcastReceiver
import android.content.IntentFilter
import androidx.core.content.ContextCompat
import com.matis.customlauncher.device.receivers.PackageInstallReceiver
import com.matis.customlauncher.device.receivers.PackageUninstallReceiver
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class LauncherApplication : Application() {

    @Inject lateinit var packageInstallReceiver: PackageInstallReceiver
    @Inject lateinit var packageUninstallReceiver: PackageUninstallReceiver

    override fun onCreate() {
        super.onCreate()

        registerReceiversOnAppStart()
    }

    private fun registerReceiversOnAppStart() {
        register(packageInstallReceiver, packageInstallReceiver.intentFilter())
        register(packageUninstallReceiver, packageUninstallReceiver.intentFilter())
    }

    private fun register(receiver: BroadcastReceiver, intentFilter: IntentFilter) {
        ContextCompat.registerReceiver(
            applicationContext,
            receiver,
            intentFilter,
            ContextCompat.RECEIVER_EXPORTED
        )
    }
}
