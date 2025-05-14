package com.matis.customlauncher.ui.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreenPager(
    isInEditMode: Boolean,
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState(pageCount = { 1 }),
    content: @Composable (page: Int) -> Unit,
) {
    val animatedPadding by animateDpAsState(
        targetValue = if (isInEditMode) 32.dp else 0.dp
    )


    HorizontalPager(
        state = pagerState,
        pageSpacing = 16.dp,
        contentPadding = PaddingValues(all = animatedPadding),
        modifier = modifier.fillMaxSize()
    ) { page ->
        content(page)
    }
}

@Composable
fun PagerIndicators(state: PagerState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(state.pageCount) { iteration ->
            val color =
                if (state.currentPage == iteration) MaterialTheme.colorScheme.primary else Color.DarkGray
            val size by animateDpAsState(
                targetValue = if (state.currentPage == iteration) 12.dp else 8.dp,
                label = "indicator size"
            )
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(size)
            )
        }
    }
}

