package com.matis.customlauncher.ui.appsearch

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.matis.customlauncher.model.view.ApplicationInfoViewDto
import com.matis.customlauncher.ui.appsearch.data.model.UiState
import com.matis.customlauncher.ui.shared.RoundedTextField
import com.matis.customlauncher.ui.shared.getApplicationIcon

@Composable
fun AppDrawerContent(
    onSearchQueryChanged: (String) -> Unit,
    uiState: UiState,
    onBackPressed: () -> Unit,
    onApplicationClicked: (String) -> Unit,
    onAddToHomeScreenClicked: (ApplicationInfoViewDto) -> Unit,
    onRemoveFromHomeScreenClicked: (ApplicationInfoViewDto) -> Unit
) {
    BackHandler {
        onBackPressed()
    }
    ApplicationList(
        onSearchQueryChanged = onSearchQueryChanged,
        uiState = uiState,
        onApplicationClicked = onApplicationClicked,
        onAddToHomeScreenClicked = onAddToHomeScreenClicked,
        onRemoveFromHomeScreenClicked = onRemoveFromHomeScreenClicked,
    )
}

@Composable
private fun ApplicationList(
    onSearchQueryChanged: (String) -> Unit,
    uiState: UiState,
    onApplicationClicked: (String) -> Unit,
    onAddToHomeScreenClicked: (ApplicationInfoViewDto) -> Unit,
    onRemoveFromHomeScreenClicked: (ApplicationInfoViewDto) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(Modifier.height(36.dp))
        }
        item {
            RoundedTextField(
                text = uiState.query,
                onValueChange = { onSearchQueryChanged(it) },
                modifier = Modifier.fillMaxWidth()
            )
        }
        items(
            items = uiState.applications,
            key = { it.packageName }
        ) { application ->
            TransparentApplication(
                application = application,
                onApplicationClicked = { onApplicationClicked(application.packageName) },
                onAddToHomeScreenClicked = onAddToHomeScreenClicked,
                onRemoveFromHomeScreenClicked = onRemoveFromHomeScreenClicked
            )
        }
        item {
            Spacer(
                Modifier.height(
                    WindowInsets.safeContent.asPaddingValues().calculateTopPadding()
                )
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TransparentApplication(
    application: ApplicationInfoViewDto,
    onApplicationClicked: () -> Unit,
    onAddToHomeScreenClicked: (ApplicationInfoViewDto) -> Unit,
    onRemoveFromHomeScreenClicked: (ApplicationInfoViewDto) -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val icon = remember { context.getApplicationIcon(application.packageName) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp)
            .pointerInput(true) {
                detectTapGestures(
                    onTap = { onApplicationClicked() },
                    onLongPress = { menuExpanded = true }
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = icon,
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(32.dp)
        )
        DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false },
        ) {
            if (application.isHomeScreenApplication) RemoveFromHomeScreenMenuItem {
                menuExpanded = false
                onRemoveFromHomeScreenClicked(application)
            }
            else AddToHomeScreenMenuItem {
                menuExpanded = false
                onAddToHomeScreenClicked(application)
            }
        }
        Text(
            text = application.label,
            color = Color.White
        )
    }
}

@Composable
fun AddToHomeScreenMenuItem(
    onClick: () -> Unit
) {
    DropdownMenuItem(
        text = { Text(text = "Add to home screen") },
        onClick = { onClick() }
    )
}

@Composable
fun RemoveFromHomeScreenMenuItem(
    onClick: () -> Unit
) {
    DropdownMenuItem(
        text = { Text(text = "Remove from home screen") },
        onClick = { onClick() }
    )
}
