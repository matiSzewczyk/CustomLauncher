package com.matis.customlauncher.ui.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTouchInput
import com.matis.customlauncher.ui.home.data.model.UiState
import org.junit.Rule
import org.junit.Test
import pl.matis.customlauncher.testing.data.homeScreenViewData

class HomeContentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun shouldDisplayTwelveTiles_whenLayoutIs3x4Grid() {
        composeTestRule.setContent {
            HomeContent(
                uiState = UiState(
                    isInEditMode = false,
                    homeScreen = homeScreenViewData
                ),
                onApplicationClicked = {},
                onRemoveFromHomeScreenClicked = {},
                onHomeScreenLongPressed = {},
                enableUserScroll = {},
                disableUserScroll = {},
                onBackPressed = {},
                onSettingsClicked = {},
                onNewPageClicked = {},
                onRemoveApplicationsPageClicked = {},
                onApplicationsPageClicked = {}
            )
        }

        composeTestRule.onAllNodesWithTag("Application tile")
            .assertCountEquals(12)
    }

    @Test
    fun shouldBeInEditMode_whenPerformingLongClickOnParentLayout() {
        var isInEditMode = false

        composeTestRule.setContent {
            HomeContent(
                uiState = UiState(
                    isInEditMode = isInEditMode,
                    homeScreen = homeScreenViewData
                ),
                onApplicationClicked = {},
                onRemoveFromHomeScreenClicked = {},
                onHomeScreenLongPressed = {isInEditMode = true},
                enableUserScroll = {},
                disableUserScroll = {},
                onBackPressed = {},
                onSettingsClicked = {},
                onNewPageClicked = {},
                onRemoveApplicationsPageClicked = {},
                onApplicationsPageClicked = {}
            )
        }

        composeTestRule.onNodeWithTag("Parent home wrapper")
            .performTouchInput { longClick() }

        assert(isInEditMode)
    }
}
