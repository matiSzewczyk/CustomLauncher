package com.matis.customlauncher.ui.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import com.matis.customlauncher.ui.AppState
import com.matis.customlauncher.ui.shared.RoundedTextField

@Composable
fun AppSearchContent(
    viewModel: AppSearchViewModel,
    appState: AppState,
    onBackPressed: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var backGroundColor by remember { mutableStateOf(Color.Gray.copy(alpha = .5f)) }
    val isSearchFocused = remember { mutableStateOf(false) }
    LaunchedEffect(null) {
        focusRequester.requestFocus()

    }
    BackHandler {
        onBackPressed()
        backGroundColor = Color.Red
    }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(null) {
                    detectTapGestures(
                        onTap = { if (isSearchFocused.value) focusManager.clearFocus() }
                    )
                }
                .safeContentPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RoundedTextField(
                text = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { isSearchFocused.value = it.isFocused }
            )
        }
}
