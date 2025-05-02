package com.matis.customlauncher.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matis.customlauncher.core.data.repository.SettingsRepository
import com.matis.customlauncher.domain.home.GetHomeScreens
import com.matis.customlauncher.domain.home.RemoveApplicationFromHomeScreen
import com.matis.customlauncher.model.domain.HomePageLayoutType
import com.matis.customlauncher.model.domain.MainPage
import com.matis.customlauncher.model.view.HomeScreenItemDto
import com.matis.customlauncher.model.view.HomeScreenPageViewDto
import com.matis.customlauncher.ui.home.data.HomeScreenViewDto
import com.matis.customlauncher.ui.home.data.model.UiState
import com.matis.customlauncher.ui.home.data.toView
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    settingsRepository: SettingsRepository,
    private val getHomeScreens: GetHomeScreens,
    private val removeApplicationFromHomeScreen: RemoveApplicationFromHomeScreen
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getHomeScreens()
                .distinctUntilChanged()
                .map { it.toView() }
                .flatMapLatest { homeScreen ->
                    combine(
                        flowOf(homeScreen),
                        settingsRepository.getLayoutForPage(MainPage.HOME)
                    ) { homeScreen, layoutType ->
                        fillEmptyPositionsWithEmptyItems(homeScreen, layoutType)
                    }
                }
                .collect { homeScreen -> _uiState.update { it.copy(homeScreen = homeScreen) } }
        }
    }

    fun onRemoveFromHomeScreenClicked(packageName: String) {
        viewModelScope.launch(Dispatchers.IO) { removeApplicationFromHomeScreen(packageName) }
    }

    fun onHomeScreenLongPressed() {
        _uiState.update {
            it.copy(
                isInEditMode = true,
                homeScreen = uiState.value.homeScreen.appendAddNewPage()
            )
        }
    }

    fun onBackPressed() {
        if (!uiState.value.isInEditMode) return
        _uiState.update {
            it.copy(
                isInEditMode = false,
                homeScreen = uiState.value.homeScreen.removeAddNewPage()
            )
        }
    }

    fun onNewPageClicked() {
        _uiState.update {
            it.copy(
                isInEditMode = false,
                homeScreen = uiState.value.homeScreen.removeAddNewPage()
            )
        }
    }

    private fun fillEmptyPositionsWithEmptyItems(
        homeScreen: HomeScreenViewDto,
        layoutType: HomePageLayoutType
    ): HomeScreenViewDto =
        homeScreen.pages.map { page ->
            val applicationsPage = page as HomeScreenPageViewDto.Applications
            val takenPages = applicationsPage.items.map { it.position }

            (0 until layoutType.appCap)
                .filterNot { takenPages.contains(it) }
                .map { HomeScreenItemDto.Empty(it) }
                .let { page.items + it }
                .sortedBy { it.position }
                .let { page.copy(items = (it)) }
        }.let { homeScreen.copy(pages = it, layoutType = layoutType) }

    private fun HomeScreenViewDto.appendAddNewPage(): HomeScreenViewDto {
        val homeScreensWithNewEmptyPage = buildList {
            addAll(pages)
            add(HomeScreenPageViewDto.AddNew(pages.size))
        }
        return copy(pages = homeScreensWithNewEmptyPage)
    }

    private fun HomeScreenViewDto.removeAddNewPage(): HomeScreenViewDto {
        val homeScreensWithoutNewEmptyPage = pages.filter { it !is HomeScreenPageViewDto.AddNew }
        return copy(pages = homeScreensWithoutNewEmptyPage)
    }
    // TODO:  implement later
    //
    //    private fun HomeScreenViewDto.addNewApplicationsPages(): HomeScreenViewDto {
    //        val homeScreensWithNewApplicationsPages = buildList {
    //            addAll(pages)
    //            add(Applications(applications = emptyList()))
    //        }
    //        return copy(pages = homeScreensWithNewApplicationsPages)
    //    }
}


