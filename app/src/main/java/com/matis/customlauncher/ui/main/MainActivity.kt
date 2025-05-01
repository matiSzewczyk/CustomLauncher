package com.matis.customlauncher.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.matis.customlauncher.core.data.repository.EnvironmentRepository
import com.matis.customlauncher.navigation.MainNavHost
import com.matis.customlauncher.ui.rememberAppState
import com.matis.customlauncher.ui.theme.CustomLauncherTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var repository: EnvironmentRepository

    override fun onStart() {
        super.onStart()
        repository.updateDefaultHomeApplicationStatus()
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val appState = rememberAppState(repository = repository)

            CustomLauncherTheme {
                MainNavHost(appState = appState)
            }
        }
    }
}

