package com.matis.customlauncher.ui.home

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.matis.customlauncher.model.HomeScreenApplicationViewItem.ApplicationItem
import com.matis.customlauncher.ui.home.data.model.UiState
import com.matis.customlauncher.ui.shared.getApplicationIcon

// TODO: Only temporary
const val COLUMNS = 3

@Composable
fun HomeContent(
    uiState: UiState,
    onApplicationClicked: (String) -> Unit,
    onRemoveFromHomeScreenClicked: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(COLUMNS),
        userScrollEnabled = false,
        verticalArrangement = Arrangement.SpaceAround,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(uiState.applications) { index, app ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                if (app is ApplicationItem) {
                    TransparentApplication(
                        application = app,
                        onApplicationClicked = onApplicationClicked,
                        onRemoveFromHomeScreenClicked = onRemoveFromHomeScreenClicked
                    )
                } else {
                    EmptyApplication()
                }
            }
        }
    }
}

@Composable
private fun TransparentApplication(
    application: ApplicationItem,
    onApplicationClicked: (String) -> Unit,
    onRemoveFromHomeScreenClicked: (String) -> Unit
) {
    var pressOffset by remember { mutableStateOf(DpOffset(0.dp, 0.dp)) }
    var menuExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
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
                .size(32.dp)
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
        Text(
            text = application.label,
            color = Color.White
        )
    }
}

@Composable
fun EmptyApplication() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
    }
}
