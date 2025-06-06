package com.matis.customlauncher.ui.settings

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopBar() {
    LargeTopAppBar(
        title = { Text(text = "Settings") }
    )
}
