package com.matis.customlauncher.ui.appsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matis.customlauncher.domain.appsearch.AddApplicationToHomeScreen
import com.matis.customlauncher.domain.appsearch.GetApplicationsMatchingQuery
import com.matis.customlauncher.domain.home.GetHomeScreenApplications
import com.matis.customlauncher.domain.home.RemoveApplicationFromHomeScreen
import com.matis.customlauncher.model.domain.ApplicationInfoDto
import com.matis.customlauncher.model.view.ApplicationInfoViewDto
import com.matis.customlauncher.model.view.toDomain
import com.matis.customlauncher.model.view.toView
import com.matis.customlauncher.ui.appsearch.data.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.matis.customlauncher.common.DispatcherProvider
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class AppDrawerViewModel @Inject constructor(
    private val addApplicationToHomeScreen: AddApplicationToHomeScreen,
    private val getApplicationsMatchingQuery: GetApplicationsMatchingQuery,
    private val getHomeScreenApplications: GetHomeScreenApplications,
    private val removeApplicationFromHomeScreen: RemoveApplicationFromHomeScreen,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState get() = _uiState.asStateFlow()

    init {
        initializeSearchQueryListener()
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(query = query) }
    }

    fun onAddToHomeScreenClicked(application: ApplicationInfoViewDto) {
        viewModelScope.launch(dispatcher.io) { addApplicationToHomeScreen(application.toDomain()) }
    }

    fun onRemoveFromHomeScreenClicked(application: ApplicationInfoViewDto) {
        viewModelScope.launch(dispatcher.io) { removeApplicationFromHomeScreen(application.packageName) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun initializeSearchQueryListener() {
        viewModelScope.launch {
            _uiState
                .map { it.query }
                .debounce(200)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    combine(
                        getApplicationsMatchingQuery(query),
                        getHomeScreenApplications()
                    ) { applications, homeScreenApps ->
                        val isHomeScreenApp = { app: ApplicationInfoDto ->
                            app.packageName in homeScreenApps.map { it.packageName }
                        }
                        applications.map { app -> app.toView(isHomeScreenApp(app)) }
                    }
                }
                .flowOn(dispatcher.default)
                .collect { filteredApps -> _uiState.update { it.copy(applications = filteredApps) } }
        }
    }
}

