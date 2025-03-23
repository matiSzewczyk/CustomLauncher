package com.matis.customlauncher

import android.app.Application
import androidx.core.content.ContextCompat
import com.matis.customlauncher.device.receivers.ApplicationInstalledReceiver
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class LauncherApplication : Application() {

    @Inject lateinit var applicationInstalledReceiver: ApplicationInstalledReceiver

    override fun onCreate() {
        super.onCreate()

        registerReceiversOnAppStart()
    }

    private fun registerReceiversOnAppStart() {
        ContextCompat.registerReceiver(
            applicationContext,
            applicationInstalledReceiver,
            applicationInstalledReceiver.intentFilter(),
            ContextCompat.RECEIVER_EXPORTED
        )
    }
}
