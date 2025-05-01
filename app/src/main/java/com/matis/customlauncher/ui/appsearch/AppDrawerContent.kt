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
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.matis.customlauncher.model.ApplicationInfoViewDto
import com.matis.customlauncher.ui.appsearch.data.model.UiState
import com.matis.customlauncher.ui.shared.RoundedTextField
import com.matis.customlauncher.ui.shared.getApplicationIcon

@Composable
fun AppDrawerContent(
    onSearchQueryChanged: (String) -> Unit,
    uiState: UiState,
    onBackPressed: () -> Unit,
    clearFocusAndHideKeyboard: () -> Unit,
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
        clearFocusAndHideKeyboard = clearFocusAndHideKeyboard,
        onApplicationClicked = onApplicationClicked,
        onAddToHomeScreenClicked = onAddToHomeScreenClicked,
        onRemoveFromHomeScreenClicked = onRemoveFromHomeScreenClicked,
    )
}

@Composable
private fun ApplicationList(
    onSearchQueryChanged: (String) -> Unit,
    uiState: UiState,
    clearFocusAndHideKeyboard: () -> Unit,
    onApplicationClicked: (String) -> Unit,
    onAddToHomeScreenClicked: (ApplicationInfoViewDto) -> Unit,
    onRemoveFromHomeScreenClicked: (ApplicationInfoViewDto) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val isSearchFocused = remember { mutableStateOf(false) }

    LaunchedEffect(null) {
        focusRequester.requestFocus()
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .pointerInput(null) {
                detectTapGestures(
                    onTap = { clearFocusAndHideKeyboard() }
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(Modifier.height(WindowInsets.statusBars.asPaddingValues().calculateTopPadding()))
        }
        item {
            RoundedTextField(
                text = uiState.query,
                onValueChange = { onSearchQueryChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { isSearchFocused.value = it.isFocused }
            )
        }
        items(uiState.applications) { application ->
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
    var pressOffset by remember { mutableStateOf(DpOffset(0.dp, 0.dp)) }
    var menuExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp)
            .pointerInput(true) {
                detectTapGestures(
                    onTap = { onApplicationClicked() },
                    onLongPress = { offset ->
                        menuExpanded = true
                        pressOffset = DpOffset(offset.x.toDp(), offset.y.toDp())
                    }
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = context.getApplicationIcon(application.packageName),
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(32.dp)
        )
        DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false },
            offset = pressOffset.copy(y = pressOffset.y - 82.dp),
        ) {
            if (application.isHomeScreenApplication) RemoveFromHomeScreenMenuItem {
                onRemoveFromHomeScreenClicked(application)
                menuExpanded = false
            }
            else AddToHomeScreenMenuItem {
                onAddToHomeScreenClicked(application)
                menuExpanded = false
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
