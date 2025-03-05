package com.matis.customlauncher.ui.main

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.matis.customlauncher.ui.AppState
import com.matis.customlauncher.ui.theme.CustomLauncherTheme

@Composable
fun MainScreen(appState: AppState) {
    val hasDefaultLauncherPermission by appState.hasDefaultLauncherPermission.collectAsStateWithLifecycle()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!hasDefaultLauncherPermission) NoDefaultLauncherContent(
            onGrantPermissionClick = { context.startActivity(Intent(Settings.ACTION_HOME_SETTINGS)) }
        )
        else Greeting("foo")
    }
}

@Composable
fun NoDefaultLauncherContent(
    onGrantPermissionClick: () -> Unit = {}
) {
    Text(text = "Default launcher permission not granted. It is required for this application")
    Button(onClick = { onGrantPermissionClick() }) {
        Text("Grant permission")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CustomLauncherTheme {
        Greeting("Android")
    }
}
