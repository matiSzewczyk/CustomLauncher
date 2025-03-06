package com.matis.customlauncher.ui.main

import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.matis.customlauncher.ui.AppState
import com.matis.customlauncher.ui.theme.CustomLauncherTheme

@Composable
fun MainScreen(
    appState: AppState,
    viewModel: MainViewModel = hiltViewModel()
) {
    val hasDefaultLauncherPermission by appState.hasDefaultLauncherPermission.collectAsStateWithLifecycle()
    val context = LocalContext.current
    BackHandler {
        if (hasDefaultLauncherPermission) viewModel.onBackPressed()
        else (context as? MainActivity)?.onBackPressedDispatcher?.onBackPressed()
    }
    if (!hasDefaultLauncherPermission) NotDefaultLauncherContent(
        onGrantPermissionClick = { context.startActivity(Intent(Settings.ACTION_HOME_SETTINGS)) }
    )
    else Greeting("foo")
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
