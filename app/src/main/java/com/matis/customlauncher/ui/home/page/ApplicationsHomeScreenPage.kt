package com.matis.customlauncher.ui.home.page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.matis.customlauncher.R
import com.matis.customlauncher.model.domain.HomePageLayoutType
import com.matis.customlauncher.model.view.HomeScreenItemDto.Application
import com.matis.customlauncher.model.view.HomeScreenPageViewDto.Applications
import com.matis.customlauncher.ui.home.data.model.UiState
import com.matis.customlauncher.ui.shared.ApplicationTile
import com.matis.customlauncher.ui.shared.LocalApplicationTile
import com.matis.customlauncher.ui.shared.REMOVE_PAGE_DURATION
import com.matis.customlauncher.ui.shared.getApplicationIcon

@Composable
fun ApplicationsHomeScreenPage(
    uiState: UiState,
    page: Int,
    onApplicationClicked: (String) -> Unit,
    onRemoveFromHomeScreenClicked: (String) -> Unit,
    onHomeScreenLongPressed: () -> Unit,
    onRemoveApplicationsPageClicked: (Int) -> Unit,
    onApplicationsPageClicked: () -> Unit
) {
    val applicationTile = when (uiState.homeScreen.layoutType) {
        HomePageLayoutType.GRID_4X4 -> ApplicationTile(
            fontSize = 12.sp,
            iconSize = 36.dp
        )
        else -> ApplicationTile(
            fontSize = 14.sp,
            iconSize = 42.dp
        )
    }

    val backgroundColor by animateColorAsState(
        targetValue = if (uiState.isInEditMode) Color.Black.copy(alpha = .6f) else Color.Transparent
    )

    var isBeingRemoved by remember { mutableStateOf(false) }

    CompositionLocalProvider(LocalApplicationTile provides applicationTile) {
        AnimatedVisibility(
            visible = !isBeingRemoved,
            exit = slideOutVertically(
                targetOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(durationMillis = REMOVE_PAGE_DURATION)
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .background(
                        color = backgroundColor,
                        shape = MaterialTheme.shapes.extraLarge
                    )
                    .testTag("Parent home wrapper")
                    .pointerInput(null) {
                        detectTapGestures(
                            onTap = { onApplicationsPageClicked() },
                            onLongPress = { onHomeScreenLongPressed() }
                        )
                    }
            ) {
                if (uiState.isInEditMode && page != 0) {
                    RemovePageButton(
                        onRemoveApplicationsPageClicked = {
                            onRemoveApplicationsPageClicked(page)
                            isBeingRemoved = true
                        }
                    )
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(uiState.homeScreen.layoutType.columns),
                    userScrollEnabled = false,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(
                        items = (uiState.homeScreen.pages[page] as Applications).items
                    ) { index, app ->
                        Box(modifier = Modifier
                            .aspectRatio(1f)
                            .testTag("Application tile")) {
                            if (app is Application) {
                                TransparentApplication(
                                    application = app,
                                    onApplicationClicked = onApplicationClicked,
                                    onRemoveFromHomeScreenClicked = onRemoveFromHomeScreenClicked,
                                    showLabel = uiState.homeScreen.applicationIconConfig.showLabel && !uiState.isInEditMode
                                )
                                // TODO: account for folder when adding support
                            } else {
                                EmptyApplication()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TransparentApplication(
    application: Application,
    onApplicationClicked: (String) -> Unit,
    onRemoveFromHomeScreenClicked: (String) -> Unit,
    showLabel: Boolean
) {
    var pressOffset by remember { mutableStateOf(DpOffset(0.dp, 0.dp)) }
    var menuExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp)
            .pointerInput(true) {
                detectTapGestures(
                    onTap = { onApplicationClicked(application.packageName) },
                    onLongPress = { offset ->
                        menuExpanded = true
                        pressOffset = DpOffset(offset.x.toDp(), offset.y.toDp())
                    }
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = context.getApplicationIcon(application.packageName),
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .size(LocalApplicationTile.current.iconSize)
        )
        DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false },
            offset = pressOffset.copy(y = pressOffset.y - 82.dp),
        ) {
            DropdownMenuItem(
                text = { Text(text = "Remove from Home Screen") },
                onClick = { onRemoveFromHomeScreenClicked(application.packageName) }
            )
        }
        if (showLabel) {
            Text(
                text = application.label,
                color = Color.White,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontSize = LocalApplicationTile.current.fontSize,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun EmptyApplication(modifier: Modifier = Modifier) {
    Box(modifier = modifier) { }
}

@Composable
fun RemovePageButton(onRemoveApplicationsPageClicked: () -> Unit) {
    IconButton(
        onClick = { onRemoveApplicationsPageClicked() },
        modifier = Modifier
            .fillMaxWidth()
            .height(62.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.delete),
            tint = Color.White,
            contentDescription = null
        )
    }
}
