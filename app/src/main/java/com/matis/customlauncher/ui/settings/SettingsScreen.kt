package com.matis.customlauncher.ui.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    BackHandler {
        viewModel.onBackPressed()
    }

    LaunchedEffect(null) {
        viewModel.event.collect { event ->
            onBackPressed()
        }
    }
    SettingsContent(
        onHomeScreenLayoutClicked = viewModel::onHomeScreenLayoutClicked,
        onAppDrawerLayoutClicked = { /* TODO: Implement */ }
    )
}

@Composable
private fun SettingsContent(
    onHomeScreenLayoutClicked: () -> Unit,
    onAppDrawerLayoutClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SettingsItem(
            text = "Home screen layout",
            onClick = { onHomeScreenLayoutClicked() }
        )
        // TODO: Not yet implemented
        SettingsItem(
            text = "App drawer layout",
            onClick = { onAppDrawerLayoutClicked }
        )
    }
}

@Composable
fun SettingsItem(
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(84.dp)
            .fillMaxWidth()
            .pointerInput(null) {
                detectTapGestures(
                    onTap = { onClick() }
                )
            }
    ) {
        Text(text = text)
    }
}
