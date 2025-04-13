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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.matis.customlauncher.ui.Page
import com.matis.customlauncher.ui.appsearch.AppSearchContent
import com.matis.customlauncher.ui.appsearch.AppSearchViewModel
import com.matis.customlauncher.ui.home.HomeContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreenContent(
    viewModel: AppSearchViewModel = hiltViewModel()
) {
    val appSearchUiState by viewModel.uiState.collectAsStateWithLifecycle()

    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val pagerState = rememberPagerState(pageCount = { 2 })

    val clearFocusAndHideKeyboard: () -> Unit = {
        focusManager.clearFocus()
        softwareKeyboardController?.hide()
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
                Page.HOME.pageNumber -> HomeContent()
                Page.APP_SEARCH.pageNumber -> AppSearchContent(
                    uiState = appSearchUiState,
                    onSearchQueryChanged = viewModel::onSearchQueryChanged,
                    onBackPressed = { coroutineScope.launch { pagerState.animateScrollToPage(0) } },
                    clearFocusAndHideKeyboard = clearFocusAndHideKeyboard,
                    onPackageIconRequested = viewModel::onPackageIconRequested,
                    onApplicationClicked = viewModel::onApplicationClicked
                )
            }
        }
    }
}
