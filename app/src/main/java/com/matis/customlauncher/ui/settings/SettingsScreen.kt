package com.matis.customlauncher.ui.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.matis.customlauncher.ui.settings.data.model.LayoutDialogType

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BackHandler {
        viewModel.onBackPressed()
    }

    LaunchedEffect(null) {
        viewModel.event.collect { event ->
            onBackPressed()
        }
    }
    if (uiState.layoutDialogType != LayoutDialogType.None) {
        LayoutDialog(
            dialogType = uiState.layoutDialogType,
            onDismissRequest = viewModel::onLayoutDialogDismissed
        )
    }
    SettingsContent(
        onHomeScreenLayoutClicked = viewModel::onHomeScreenLayoutClicked,
        onAppDrawerLayoutClicked = viewModel::onAppDrawerLayoutClicked
    )
}

@Composable
private fun SettingsContent(
    onHomeScreenLayoutClicked: () -> Unit,
    onAppDrawerLayoutClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeContentPadding()
    ) {
        SettingsItem(
            text = "Home screen layout",
            onClick = { onHomeScreenLayoutClicked() }
        )
        HorizontalDivider()
        SettingsItem(
            text = "App drawer layout",
            onClick = { onAppDrawerLayoutClicked }
        )
        HorizontalDivider()
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
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text)
    }
}
