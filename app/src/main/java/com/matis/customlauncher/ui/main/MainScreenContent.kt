package com.matis.customlauncher.ui.main

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.matis.customlauncher.model.domain.MainPage
import com.matis.customlauncher.ui.appsearch.AppDrawerContent
import com.matis.customlauncher.ui.appsearch.AppDrawerViewModel
import com.matis.customlauncher.ui.component.MainScreenPager
import com.matis.customlauncher.ui.home.HomeContent
import com.matis.customlauncher.ui.home.HomeScreenViewModel
import com.matis.customlauncher.ui.main.data.model.colorForPage
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreenContent(
    appDrawerViewModel: AppDrawerViewModel = hiltViewModel(),
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    onSettingsClicked: () -> Unit
) {
    val context = LocalContext.current
    val appSearchUiState by appDrawerViewModel.uiState.collectAsStateWithLifecycle()
    val homeScreenUiState by homeScreenViewModel.uiState.collectAsStateWithLifecycle()

    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val pagerState = rememberPagerState(pageCount = { MainPage.entries.size })
    var userScrollEnabled by remember { mutableStateOf(true) }

    val clearFocusAndHideKeyboard: () -> Unit = {
        focusManager.clearFocus()
        softwareKeyboardController?.hide()
    }

    val onApplicationClicked: (String) -> Unit = { packageName ->
        context.packageManager.getLaunchIntentForPackage(packageName)
            ?.let { intent -> context.startActivity(intent) }
            ?: context.showToast("Unable to launch application")
    }

    val coroutineScope = rememberCoroutineScope()

    if (pagerState.isScrollInProgress) clearFocusAndHideKeyboard()

    val backgroundColor by animateColorAsState(
        targetValue = colorForPage(pagerState.currentPage)
    )

    MainScreenPager(
        userScrollEnabled = userScrollEnabled,
        backgroundColor = backgroundColor,
        modifier = Modifier.fillMaxSize(),
        pagerState = pagerState
    ) { page ->
        when (page) {
            MainPage.HOME.pageNumber -> HomeContent(
                uiState = homeScreenUiState,
                onApplicationClicked = onApplicationClicked,
                onRemoveFromHomeScreenClicked = homeScreenViewModel::onRemoveFromHomeScreenClicked,
                onMainScreenLongPressed = homeScreenViewModel::onHomeScreenLongPressed,
                enableUserScroll = { userScrollEnabled = true },
                disableUserScroll = { userScrollEnabled = false },
                onBackPressed = homeScreenViewModel::onBackPressed,
                onSettingsClicked = onSettingsClicked,
                onNewPageClicked = homeScreenViewModel::onNewPageClicked
            )
            MainPage.APP_DRAWER.pageNumber -> AppDrawerContent(
                uiState = appSearchUiState,
                onSearchQueryChanged = appDrawerViewModel::onSearchQueryChanged,
                onBackPressed = { coroutineScope.launch { pagerState.animateScrollToPage(0) } },
                onApplicationClicked = onApplicationClicked,
                onAddToHomeScreenClicked = appDrawerViewModel::onAddToHomeScreenClicked,
                onRemoveFromHomeScreenClicked = appDrawerViewModel::onRemoveFromHomeScreenClicked,
            )
        }
    }
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT)
        .show()
}
