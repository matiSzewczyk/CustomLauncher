package com.matis.customlauncher.ui.settings

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.matis.customlauncher.model.LayoutType
import com.matis.customlauncher.ui.settings.data.model.UiState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
    ) {
        SettingsContent(
            uiState = uiState,
            onHomeScreenLayoutClicked = viewModel::onHomeScreenLayoutClicked,
            onAppDrawerLayoutClicked = viewModel::onAppDrawerLayoutClicked,
            modifier = Modifier
                .padding(top = it.calculateTopPadding(), bottom = it.calculateTopPadding())
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun SettingsContent(
    uiState: UiState,
    modifier: Modifier = Modifier,
    onHomeScreenLayoutClicked: () -> Unit,
    onAppDrawerLayoutClicked: () -> Unit,
) {
    Box(modifier = modifier) {
        Card {
            Column {
                SettingsItem(
                    text = "Home screen layout",
                    appliedLayoutType = uiState.appliedLayoutTypeForHome.layoutType,
                    onClick = onHomeScreenLayoutClicked
                )
                HorizontalDivider()
                SettingsItem(
                    text = "App drawer layout",
                    appliedLayoutType = uiState.appliedLayoutTypeForAppDrawer.layoutType,
                    onClick = onAppDrawerLayoutClicked
                )
            }
        }
    }
}

@Composable
fun SettingsItem(
    text: String,
    appliedLayoutType: LayoutType,
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
            text = appliedLayoutType.rawName,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
