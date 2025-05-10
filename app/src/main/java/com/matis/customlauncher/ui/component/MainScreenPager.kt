package com.matis.customlauncher.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MainScreenPager(
    userScrollEnabled: Boolean = true,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState(pageCount = { 1 }),
    content: @Composable (page: Int) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        VerticalPager(
            state = pagerState,
            userScrollEnabled = userScrollEnabled
        ) {
            content(it)
        }
    }
}
