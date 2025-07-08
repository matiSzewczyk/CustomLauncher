package com.matis.customlauncher.ui.search

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import com.matis.customlauncher.ui.appsearch.AppDrawerContent
import com.matis.customlauncher.ui.appsearch.data.model.UiState
import org.junit.Rule
import org.junit.Test
import pl.matis.customlauncher.testing.data.applications

class AppDrawerContentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun shouldDisplayAllApplicationsOnDevice() {
        composeTestRule.setContent {
            AppDrawerContent(
                onSearchQueryChanged = {},
                uiState = UiState(applications = applications),
                onBackPressed = {},
                onApplicationClicked = {},
                onAddToHomeScreenClicked = {},
                onRemoveFromHomeScreenClicked = {})
        }

        composeTestRule
            .onNodeWithTag("application: ${applications[0].packageName}")
            .assertIsDisplayed()
    }

    @Test
    fun shouldExpandDropDownMenuOnLongClick() {
        composeTestRule.setContent {
            AppDrawerContent(
                onSearchQueryChanged = {},
                uiState = UiState(applications = applications),
                onBackPressed = {},
                onApplicationClicked = {},
                onAddToHomeScreenClicked = {},
                onRemoveFromHomeScreenClicked = {})
        }

        with(composeTestRule) {
            onNodeWithTag("application: ${applications[0].packageName}")
                .performTouchInput { longClick() }

            onNodeWithText("Add to home screen")
                .assertIsDisplayed()
        }
    }

    @Test
    fun shouldDisplayRemoveFromHomeScreen_whenIsHomeScreenApp() {
        val apps =
            applications.map { if (it.packageName == "com.foo") it.copy(isHomeScreenApplication = true) else it }
        composeTestRule.setContent {
            AppDrawerContent(
                onSearchQueryChanged = {},
                uiState = UiState(applications = apps),
                onBackPressed = {},
                onApplicationClicked = {},
                onAddToHomeScreenClicked = {},
                onRemoveFromHomeScreenClicked = {})
        }

        with(composeTestRule) {
            onNodeWithTag("application: com.foo")
                .performTouchInput { longClick() }

            onNodeWithText("Remove from home screen")
                .assertIsDisplayed()
        }
    }
}
