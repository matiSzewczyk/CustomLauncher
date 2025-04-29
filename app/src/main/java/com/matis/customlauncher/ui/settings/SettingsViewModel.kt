package com.matis.customlauncher.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matis.customlauncher.ui.settings.data.model.LayoutDialogType
import com.matis.customlauncher.ui.settings.data.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<Any>()
    val event get() = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(UiState())
    val uiState get() = _uiState.asStateFlow()

    fun onHomeScreenLayoutClicked() {
        _uiState.update { it.copy(layoutDialogType = LayoutDialogType.HomeScreen) }
    }

    fun onAppDrawerLayoutClicked() {
        _uiState.update { it.copy(layoutDialogType = LayoutDialogType.AppDrawer) }
    }

    fun onBackPressed(): Job = viewModelScope.launch {
        if (uiState.value.layoutDialogType != LayoutDialogType.None) {
            _uiState.update { it.copy(layoutDialogType = LayoutDialogType.None) }
        } else {
            _event.emit(Any())
        }
    }

    fun onLayoutDialogDismissed() {
        _uiState.update { it.copy(layoutDialogType = LayoutDialogType.None) }
    }
}
