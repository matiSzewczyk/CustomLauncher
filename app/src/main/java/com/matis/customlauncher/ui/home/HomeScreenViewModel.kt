package com.matis.customlauncher.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matis.customlauncher.core.data.repository.SettingsRepository
import com.matis.customlauncher.domain.home.GetHomeScreenApplications
import com.matis.customlauncher.domain.home.RemoveApplicationFromHomeScreen
import com.matis.customlauncher.model.Applications
import com.matis.customlauncher.model.AddNewApplication
import com.matis.customlauncher.model.HomeScreenApplicationDto
import com.matis.customlauncher.model.HomeScreenApplicationViewItem.ApplicationItem
import com.matis.customlauncher.model.HomeScreenApplicationViewItem.EmptyItem
import com.matis.customlauncher.model.LayoutType
import com.matis.customlauncher.model.MainPage
import com.matis.customlauncher.model.toView
import com.matis.customlauncher.ui.home.data.HomeScreenViewDto
import com.matis.customlauncher.ui.home.data.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.ceil
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
import kotlinx.coroutines.withContext

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    settingsRepository: SettingsRepository,
    private val getHomeScreenApplications: GetHomeScreenApplications,
    private val removeApplicationFromHomeScreen: RemoveApplicationFromHomeScreen
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getHomeScreenApplications()
                .distinctUntilChanged()
                .map { it.map(HomeScreenApplicationDto::toView) }
                .flatMapLatest { applications ->
                    combine(
                        flowOf(applications),
                        settingsRepository.getLayoutForPage(MainPage.HOME)
                    ) { applications, layoutType ->
                        getHomeScreen(applications, layoutType)
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
                homeScreen = uiState.value.homeScreen.addNewEmptyPage()
            )
        }
    }

    fun onBackPressed() {
        if (!uiState.value.isInEditMode) return
        _uiState.update {
            it.copy(
                isInEditMode = false,
                homeScreen = uiState.value.homeScreen.removeNewEmptyPage()
            )
        }
    }

    private suspend fun getHomeScreen(
        homeScreenApplications: List<ApplicationItem>,
        layoutType: LayoutType
    ): HomeScreenViewDto = withContext(Dispatchers.Default) {
        val positionMap = homeScreenApplications.associateBy { it.position }
        val highestPosition = positionMap.keys.maxOrNull() ?: 0
        val applicationPageCount = ceil(highestPosition.toFloat() / layoutType.appCap).toInt()

        val applicationPages = (0 until applicationPageCount).map { pageIndex ->
            val start = pageIndex * layoutType.appCap
            val end = start + layoutType.appCap
            val applications = (start until end).map { position ->
                positionMap[position] ?: EmptyItem(position)
            }
            Applications(applications = applications)
        }
        HomeScreenViewDto(layoutType = layoutType, pages = applicationPages)
    }

    private fun HomeScreenViewDto.addNewEmptyPage(): HomeScreenViewDto {
        val homeScreensWithNewEmptyPage = buildList {
            addAll(pages)
            add(AddNewApplication)
        }
        return copy(pages = homeScreensWithNewEmptyPage)
    }

    private fun HomeScreenViewDto.removeNewEmptyPage(): HomeScreenViewDto {
        val homeScreensWithoutNewEmptyPage = pages.filter { it !is AddNewApplication }
        return copy(pages = homeScreensWithoutNewEmptyPage)
    }
}


