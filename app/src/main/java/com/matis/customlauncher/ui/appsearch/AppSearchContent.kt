package com.matis.customlauncher.ui.appsearch

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.matis.customlauncher.ui.shared.RoundedTextField

@Composable
fun AppSearchContent(
    onSearchQueryChanged: (String) -> Unit,
    uiState: UiState,
    onBackPressed: () -> Unit,
    clearFocusAndHideKeyboard: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val isSearchFocused = remember { mutableStateOf(false) }

    LaunchedEffect(null) {
        focusRequester.requestFocus()
    }

    BackHandler {
        onBackPressed()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(null) {
                detectTapGestures(
                    onTap = { clearFocusAndHideKeyboard() }
                )
            }
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RoundedTextField(
            text = uiState.query,
            onValueChange = { onSearchQueryChanged(it) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged { isSearchFocused.value = it.isFocused }
        )
    }
}
