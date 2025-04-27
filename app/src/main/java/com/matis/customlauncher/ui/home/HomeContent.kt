package com.matis.customlauncher.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeContent(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    onApplicationClicked: (String) -> Unit,
    onRemoveFromHomeScreenClicked: (String) -> Unit,
    onMainScreenLongPressed: () -> Unit
) {
    val uiState by homeScreenViewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.isInEditMode) {
        EditModeHomeContent(
            onBackPressed = homeScreenViewModel::onBackPressed
        )
    } else {
        DefaultHomeContent(
            uiState = uiState,
            onApplicationClicked = onApplicationClicked,
            onRemoveFromHomeScreenClicked = onRemoveFromHomeScreenClicked,
            onMainScreenLongPressed = onMainScreenLongPressed
        )
    }
}

