package com.matis.customlauncher.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.matis.customlauncher.ui.Page
import com.matis.customlauncher.ui.appsearch.AppSearchContent
import com.matis.customlauncher.ui.appsearch.AppSearchViewModel
import com.matis.customlauncher.ui.home.HomeContent
import com.matis.customlauncher.ui.home.HomeScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreenContent(
    appSearchViewModel: AppSearchViewModel = hiltViewModel(),
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val appSearchUiState by appSearchViewModel.uiState.collectAsStateWithLifecycle()
    val homeScreenUiState by homeScreenViewModel.uiState.collectAsStateWithLifecycle()

    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val pagerState = rememberPagerState(pageCount = { 2 })

    val clearFocusAndHideKeyboard: () -> Unit = {
        focusManager.clearFocus()
        softwareKeyboardController?.hide()
    }

    val onApplicationClicked: (String) -> Unit = { packageName ->
        context.packageManager.getLaunchIntentForPackage(packageName)
            ?.let { intent -> context.startActivity(intent) }
    }

    val coroutineScope = rememberCoroutineScope()
    val colors = Page.entries.map { it.color }

    if (pagerState.isScrollInProgress) clearFocusAndHideKeyboard()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors[pagerState.currentPage])
    ) {
        VerticalPager(state = pagerState) {
            when (it) {
                Page.HOME.pageNumber -> HomeContent(
                    uiState = homeScreenUiState,
                    onApplicationClicked = onApplicationClicked,
                    onRemoveFromHomeScreenClicked = homeScreenViewModel::onRemoveFromHomeScreenClicked
                )
                Page.APP_SEARCH.pageNumber -> AppSearchContent(
                    uiState = appSearchUiState,
                    onSearchQueryChanged = appSearchViewModel::onSearchQueryChanged,
                    onBackPressed = { coroutineScope.launch { pagerState.animateScrollToPage(0) } },
                    clearFocusAndHideKeyboard = clearFocusAndHideKeyboard,
                    onApplicationClicked = onApplicationClicked,
                    onAddToHomeScreenClicked = appSearchViewModel::onAddToHomeScreenClicked
                )
            }
        }
    }
}
