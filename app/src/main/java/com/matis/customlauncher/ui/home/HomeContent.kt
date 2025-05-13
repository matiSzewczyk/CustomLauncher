package com.matis.customlauncher.ui.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.matis.customlauncher.ui.component.HomeScreenPager
import com.matis.customlauncher.ui.component.PagerIndicators
import com.matis.customlauncher.ui.home.data.model.UiState
import com.matis.customlauncher.ui.home.page.ApplicationsHomeScreenPage
import com.matis.customlauncher.ui.home.page.NewHomeScreenPage

@Composable
fun HomeContent(
    uiState: UiState,
    onApplicationClicked: (String) -> Unit,
    onRemoveFromHomeScreenClicked: (String) -> Unit,
    onHomeScreenLongPressed: () -> Unit,
    enableUserScroll: () -> Unit,
    disableUserScroll: () -> Unit,
    onBackPressed: () -> Unit,
    onSettingsClicked: () -> Unit,
    onNewPageClicked: () -> Unit,
    onRemoveApplicationsPageClicked: (Int) -> Unit
) {
    // Disable scroll for vertical pager
    if (uiState.isInEditMode) disableUserScroll() else enableUserScroll()

    val pagerState = rememberPagerState(pageCount = { uiState.homeScreen.pages.size })

    BackHandler {
        onBackPressed()
    }

    Column(modifier = Modifier.padding(vertical = 24.dp)) {
        HomeScreenPager(
            pagerState = pagerState,
            isInEditMode = uiState.isInEditMode,
            modifier = Modifier.weight(1f)
        ) { page ->
            when {
                uiState.homeScreen.pages[page] is Applications -> ApplicationsHomeScreenPage(
                    uiState = uiState,
                    page = page,
                    onApplicationClicked = onApplicationClicked,
                    onRemoveFromHomeScreenClicked = onRemoveFromHomeScreenClicked,
                    onHomeScreenLongPressed = onHomeScreenLongPressed,
                    onRemoveApplicationsPageClicked = onRemoveApplicationsPageClicked
                )
                uiState.isInEditMode -> NewHomeScreenPage(
                    onNewPageClicked = onNewPageClicked
                )
            }
        }
        PagerIndicators(state = pagerState)
        BottomInteractionSection(
            isVisible = uiState.isInEditMode,
            onSettingsClicked = onSettingsClicked
        )
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
