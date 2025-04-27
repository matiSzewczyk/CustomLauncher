package com.matis.customlauncher.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matis.customlauncher.domain.home.GetHomeScreenApplications
import com.matis.customlauncher.domain.home.RemoveApplicationFromHomeScreen
import com.matis.customlauncher.model.HomeScreenApplicationDto
import com.matis.customlauncher.model.HomeScreenApplicationViewItem
import com.matis.customlauncher.model.HomeScreenApplicationViewItem.ApplicationItem
import com.matis.customlauncher.model.HomeScreenApplicationViewItem.EmptyItem
import com.matis.customlauncher.model.toView
import com.matis.customlauncher.ui.home.data.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
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
                .map { getGridItems(it) }
                .collect { gridItems -> _uiState.update { it.copy(applications = gridItems) } }
        }
    }

    fun onRemoveFromHomeScreenClicked(packageName: String) {
        viewModelScope.launch(Dispatchers.IO) { removeApplicationFromHomeScreen(packageName) }
    }

    fun onHomeScreenLongPressed() {
        _uiState.update { it.copy(isInEditMode = true) }
    }

    fun onBackPressed() {
        _uiState.update { it.copy(isInEditMode = false) }
    }

    private fun getGridItems(homeScreenApplications: List<ApplicationItem>): List<HomeScreenApplicationViewItem> {
        val positionMap = homeScreenApplications.associateBy { it.position }
        return List(GRID_SIZE) { position ->
            positionMap[position] ?: EmptyItem(position)
        }
    }

    companion object {
        // TODO: Temporary, use customizable, user picked values
        private const val GRID_SIZE = 12
    }
}
