package com.matis.customlauncher.ui.appsearch

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matis.customlauncher.device.PackagesApi
import com.matis.customlauncher.domain.PackagesService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
@HiltViewModel
class AppSearchViewModel @Inject constructor(
    private val packagesService: PackagesService,
    private val packagesApi: PackagesApi
) : ViewModel() {

    var uiState = MutableStateFlow(UiState())
        private set

    init {
        initializeSearchQueryListener()
    }

    fun onSearchQueryChanged(query: String) {
        uiState.update { it.copy(query = query) }
    }

    fun onPackageIconRequested(packageName: String): Drawable? =
        packagesApi.getPackageIcon(packageName)

    fun onApplicationClicked(packageName: String) {
        packagesApi.openApplication(packageName)
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
                .flatMapLatest { packagesService.getApplications(query = it) }
                .collect { apps ->
                    Log.d("TEST", "Applications: ${apps.map { it.label }}")
                    uiState.update { it.copy(applications = apps) }
                }
        }
    }
}

