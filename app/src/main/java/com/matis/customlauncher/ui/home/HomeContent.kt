package com.matis.customlauncher.ui.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.matis.customlauncher.R
import com.matis.customlauncher.model.view.HomeScreenPageViewDto.Applications
import com.matis.customlauncher.ui.home.data.model.UiState
import com.matis.customlauncher.ui.home.page.ApplicationsHomeScreenPage
import com.matis.customlauncher.ui.home.page.NewHomeScreenPage

@Composable
fun HomeContent(
    uiState: UiState,
    onApplicationClicked: (String) -> Unit,
    onRemoveFromHomeScreenClicked: (String) -> Unit,
    onMainScreenLongPressed: () -> Unit,
    enableUserScroll: () -> Unit,
    disableUserScroll: () -> Unit,
    onBackPressed: () -> Unit,
    onSettingsClicked: () -> Unit,
    onNewPageClicked: () -> Unit
) {
    // Disable scroll for vertical pager
    if (uiState.isInEditMode) disableUserScroll() else enableUserScroll()

    BackHandler {
        onBackPressed()
    }

    Column(
        modifier = Modifier.padding(vertical = 24.dp)
    ) {
        HomeScreenPagerSection(
            uiState = uiState,
            onApplicationClicked = onApplicationClicked,
            onRemoveFromHomeScreenClicked = onRemoveFromHomeScreenClicked,
            onMainScreenLongPressed = onMainScreenLongPressed,
            onNewPageClicked = onNewPageClicked,
            modifier = Modifier.weight(1f)
        )
        BottomInteractionSection(
            isVisible = uiState.isInEditMode,
            onSettingsClicked = onSettingsClicked
        )
    }
}

@Composable
fun HomeScreenPagerSection(
    uiState: UiState,
    onApplicationClicked: (String) -> Unit,
    onRemoveFromHomeScreenClicked: (String) -> Unit,
    onMainScreenLongPressed: () -> Unit,
    onNewPageClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedPadding by animateDpAsState(
        targetValue = if (uiState.isInEditMode) 32.dp else 0.dp
    )

    val state = rememberPagerState(pageCount = { uiState.homeScreen.pages.size })

    HorizontalPager(
        state = state,
        pageSpacing = 16.dp,
        contentPadding = PaddingValues(all = animatedPadding),
        modifier = modifier.fillMaxSize()
    ) { page ->
        when {
            uiState.homeScreen.pages[page] is Applications -> ApplicationsHomeScreenPage(
                uiState = uiState,
                page = page,
                onApplicationClicked = onApplicationClicked,
                onRemoveFromHomeScreenClicked = onRemoveFromHomeScreenClicked,
                onMainScreenLongPressed = onMainScreenLongPressed
            )
            uiState.isInEditMode -> NewHomeScreenPage(
                onNewPageClicked = onNewPageClicked
            )
        }
    }
}

@Composable
fun BottomInteractionSection(
    isVisible: Boolean,
    onSettingsClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedOffset by animateDpAsState(
        targetValue = if (isVisible) 0.dp else 64.dp
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(bottom = animatedOffset),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        EditModeInteractionButton(
            onClick = { onSettingsClicked() },
            text = "Settings",
            icon = painterResource(R.drawable.settings)
        )
        EditModeInteractionButton(
            onClick = {},
            text = "Wallpaper and style",
            icon = painterResource(R.drawable.wallpaper)
        )
    }
}

@Composable
private fun EditModeInteractionButton(
    onClick: () -> Unit,
    text: String,
    icon: Painter
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.pointerInput(null) {
            detectTapGestures(
                onTap = { onClick() }
            )
        }
    ) {
        Icon(
            painter = icon,
            tint = Color.White,
            contentDescription = null
        )
        Text(
            text = text,
            color = Color.White,
            maxLines = 2
        )
    }
}
