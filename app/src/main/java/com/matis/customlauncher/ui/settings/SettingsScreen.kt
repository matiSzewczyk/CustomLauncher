package com.matis.customlauncher.ui.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.matis.customlauncher.ui.component.SettingsCard

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
    if (uiState.layoutDialogToDisplay != null) {
        LayoutDialog(
            uiState = uiState,
            onDismissRequest = viewModel::onLayoutDialogDismissed,
            onConfirmClicked = viewModel::onConfirmClicked
        )
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { SettingsTopBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
        ) {
            SettingsCard {
                SettingsItem(
                    text = "Home screen layout",
                    appliedSetting = uiState.appliedLayoutTypeForHome.layoutType.rawName,
                    onClick = viewModel::onHomeScreenLayoutClicked
                )
                HorizontalDivider()
                SettingsItem(
                    text = "App drawer layout",
                    appliedSetting = uiState.appliedLayoutTypeForAppDrawer.layoutType.rawName,
                    onClick = viewModel::onAppDrawerLayoutClicked
                )
            }
            SettingsCard {
                SettingsToggleItem(
                    text = "Show application label",
                    showApplicationLabel = uiState.applicationIconConfig.showLabel,
                    onShowApplicationLabelChanged = viewModel::onShowApplicationLabelChanged
                )
                HorizontalDivider()
                SettingsItem(
                    text = "Icon Shape",
                    appliedSetting = uiState.applicationIconConfig.iconShape,
                    onClick = {/* NOT YET IMPLEMENTED */ }
                )
            }
        }
    }
}

@Composable
private fun SettingsItem(
    text: String,
    appliedSetting: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(84.dp)
            .fillMaxWidth()
            .clickable(
                onClick = { onClick() },
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple()
            )
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = text)
        Text(
            text = appliedSetting,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun SettingsToggleItem(
    text: String,
    showApplicationLabel: Boolean,
    onShowApplicationLabelChanged: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .height(84.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = text)
        Switch(
            checked = showApplicationLabel,
            onCheckedChange = { onShowApplicationLabelChanged(!showApplicationLabel) }
        )
    }
}
