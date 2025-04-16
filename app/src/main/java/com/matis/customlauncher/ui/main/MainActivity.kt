package com.matis.customlauncher.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.matis.customlauncher.navigation.MainNavHost
import com.matis.customlauncher.ui.rememberAppState
import com.matis.customlauncher.ui.theme.CustomLauncherTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var packagesApi: PackagesApi

    override fun onStart() {
        super.onStart()
        packagesApi.checkIfIsDefaultHomeApp()
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val appState = rememberAppState(packagesApi)

            CustomLauncherTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.Transparent.copy(alpha = .15f)
                ) {
                    MainNavHost(appState = appState)
                }
            }
        }
    }
}

