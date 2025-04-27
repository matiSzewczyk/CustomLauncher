package com.matis.customlauncher.ui.appsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matis.customlauncher.domain.appsearch.AddApplicationToHomeScreen
import com.matis.customlauncher.domain.appsearch.GetApplicationsMatchingQuery
import com.matis.customlauncher.domain.home.GetHomeScreenApplications
import com.matis.customlauncher.domain.home.RemoveApplicationFromHomeScreen
import com.matis.customlauncher.model.ApplicationInfoDto
import com.matis.customlauncher.model.ApplicationInfoViewDto
import com.matis.customlauncher.model.toDomain
import com.matis.customlauncher.model.toView
import com.matis.customlauncher.ui.appsearch.data.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
@HiltViewModel
class AppSearchViewModel @Inject constructor(
    private val addApplicationToHomeScreen: AddApplicationToHomeScreen,
    private val getApplicationsMatchingQuery: GetApplicationsMatchingQuery,
    private val getHomeScreenApplications: GetHomeScreenApplications,
    private val removeApplicationFromHomeScreen: RemoveApplicationFromHomeScreen
) : ViewModel() {

    var uiState = MutableStateFlow(UiState())
        private set

    init {
        initializeSearchQueryListener()
    }

    fun onSearchQueryChanged(query: String) {
        uiState.update { it.copy(query = query) }
    }

    fun onAddToHomeScreenClicked(application: ApplicationInfoViewDto) {
        viewModelScope.launch(Dispatchers.IO) { addApplicationToHomeScreen(application.toDomain()) }
    }

    fun onRemoveFromHomeScreenClicked(application: ApplicationInfoViewDto) {
        viewModelScope.launch(Dispatchers.IO) { removeApplicationFromHomeScreen(application.packageName) }
    }

    override fun onCleared() {
        super.onCleared()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun initializeSearchQueryListener() {
        viewModelScope.launch {
            uiState
                .map { it.query }
                .debounce(timeoutMillis = 300)
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
                .collect { filteredApps -> uiState.update { it.copy(applications = filteredApps) } }
        }
    }
}

