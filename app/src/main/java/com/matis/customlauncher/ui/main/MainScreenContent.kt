package com.matis.customlauncher.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.matis.customlauncher.ui.AppState
import com.matis.customlauncher.ui.Page
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreenContent(
    viewModel: AppSearchViewModel,
    appState: AppState
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()
    val colors = Page.entries.map { it.color }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors[pagerState.currentPage])
    ) {
        VerticalPager(state = pagerState) {
            when (it) {
                Page.HOME.pageNumber -> WidgetContent()
                Page.APP_SEARCH.pageNumber -> AppSearchContent(
                    viewModel = viewModel,
                    appState = appState,
                    onBackPressed = { coroutineScope.launch { pagerState.animateScrollToPage(0) } }
                )
            }
        }
    }
}
