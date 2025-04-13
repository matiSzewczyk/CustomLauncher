package com.matis.customlauncher.ui.appsearch

import android.graphics.drawable.Drawable
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.matis.customlauncher.ui.shared.RoundedTextField

@Composable
fun AppSearchContent(
    onSearchQueryChanged: (String) -> Unit,
    uiState: UiState,
    onBackPressed: () -> Unit,
    clearFocusAndHideKeyboard: () -> Unit,
    onPackageIconRequested: (String) -> Drawable?
) {
    val focusRequester = remember { FocusRequester() }
    val isSearchFocused = remember { mutableStateOf(false) }

    LaunchedEffect(null) {
        focusRequester.requestFocus()
    }

    BackHandler {
        onBackPressed()
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
        items(uiState.applications) {
            TransparentApplication(
                label = it.label,
                icon = onPackageIconRequested(it.packageName)
            )
        }
    }
}

@Composable
private fun TransparentApplication(
    label: String,
    icon: Drawable?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = icon,
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(32.dp)
        )
        Text(text = label, color = Color.White)
    }
}
