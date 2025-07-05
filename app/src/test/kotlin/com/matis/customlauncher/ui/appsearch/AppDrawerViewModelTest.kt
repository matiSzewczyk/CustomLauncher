package com.matis.customlauncher.ui.appsearch

import com.matis.customlauncher.domain.appsearch.AddApplicationToHomeScreen
import com.matis.customlauncher.domain.appsearch.GetApplicationsMatchingQuery
import com.matis.customlauncher.domain.home.GetHomeScreenApplications
import com.matis.customlauncher.domain.home.RemoveApplicationFromHomeScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.matis.customlauncher.testing.MainDispatcherRule
import pl.matis.customlauncher.testing.TestDispatcherProvider
import pl.matis.customlauncher.testing.data.installedApplications
import pl.matis.customlauncher.testing.repository.TestHomeScreenRepository
import pl.matis.customlauncher.testing.repository.TestInstalledApplicationsRepository

@OptIn(ExperimentalCoroutinesApi::class)
class AppDrawerViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val homeScreenRepository = TestHomeScreenRepository()
    private val installedApplicationsRepository = TestInstalledApplicationsRepository()

    private lateinit var viewModel: AppDrawerViewModel

    @Before
    fun setUp() {
        viewModel = AppDrawerViewModel(
            addApplicationToHomeScreen = AddApplicationToHomeScreen(
                repository = homeScreenRepository
            ),
            getApplicationsMatchingQuery = GetApplicationsMatchingQuery(
                repository = installedApplicationsRepository
            ),
            getHomeScreenApplications = GetHomeScreenApplications(
                repository = homeScreenRepository
            ),
            removeApplicationFromHomeScreen = RemoveApplicationFromHomeScreen(
                repository = homeScreenRepository
            ),
            dispatcher = TestDispatcherProvider()
        )
    }

    @Test
    fun `Start with full list of applications`() = runTest {
        // Given
        val expected = installedApplications.map { it.packageName }.sorted()
        // When
        advanceUntilIdle()

        // Then
        assertEquals(expected, viewModel.uiState.value.applications.map { it.packageName })
    }


    @Test
    fun `Should filter applications based on query`() = runTest {
        // Given
        val query = "foo"
        val expected = installedApplications.map { it.packageName }.filter {
            it.contains(query, ignoreCase = true)
        }

        // When
        viewModel.onSearchQueryChanged(query)
        advanceUntilIdle()

        // Then
        assertEquals(expected, viewModel.uiState.value.applications.map { it.packageName })
    }
}
