package com.matis.customlauncher.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matis.customlauncher.core.data.repository.SettingsRepository
import com.matis.customlauncher.domain.settings.SaveNewApplicationIconConfig
import com.matis.customlauncher.domain.settings.SaveNewLayoutConfig
import com.matis.customlauncher.model.domain.MainPage
import com.matis.customlauncher.model.domain.PageLayoutDto
import com.matis.customlauncher.ui.settings.data.model.LayoutDialogType
import com.matis.customlauncher.ui.settings.data.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val saveNewLayoutConfig: SaveNewLayoutConfig,
    private val saveNewApplicationIconConfig: SaveNewApplicationIconConfig,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                settingsRepository.getLayoutForPage(MainPage.HOME),
                settingsRepository.getLayoutForPage(MainPage.APP_DRAWER),
                settingsRepository.getApplicationIconConfig()
            ) { home, appDrawer, config ->
                val homeState = uiState.value.appliedLayoutTypeForHome
                val appDrawerState = uiState.value.appliedLayoutTypeForAppDrawer
                uiState.value.copy(
                    appliedLayoutTypeForHome = homeState.copy(layoutType = home),
                    appliedLayoutTypeForAppDrawer = appDrawerState.copy(layoutType = appDrawer),
                    applicationIconConfig = config
                )
            }.collect { state -> _uiState.update { state } }
        }
    }

    fun onHomeScreenLayoutClicked() {
        _uiState.update { it.copy(layoutDialogToDisplay = LayoutDialogType.Home) }
    }

    fun onAppDrawerLayoutClicked() {
        _uiState.update { it.copy(layoutDialogToDisplay = LayoutDialogType.AppDrawer) }
    }

    fun onLayoutDialogDismissed() {
        _uiState.update { it.copy(layoutDialogToDisplay = null) }
    }

    fun onConfirmClicked(result: PageLayoutDto) {
        viewModelScope.launch {
            _uiState.update { it.copy(layoutDialogToDisplay = null) }
            saveNewLayoutConfig(result)
        }
    }

    fun onShowApplicationLabelChanged(showLabel: Boolean) {
        viewModelScope.launch {
            val config = uiState.value.applicationIconConfig.copy(showLabel = showLabel)
            _uiState.update { it.copy(applicationIconConfig = config) }
            saveNewApplicationIconConfig(config)
        }
    }
}
