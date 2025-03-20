package com.matis.customlauncher.ui.appsearch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
@HiltViewModel
class AppSearchViewModel @Inject constructor() : ViewModel() {

    var uiState = MutableStateFlow(UiState())
        private set

    init {
        initializeSearchQueryListener()
    }

    fun onSearchQueryChanged(query: String) {
        uiState.update { it.copy(query = query) }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("TEST", "onCleared: ")
    }

    private fun initializeSearchQueryListener() {
        viewModelScope.launch {
            uiState
                .map { it.query }
                .debounce(300)
                .distinctUntilChanged()
                .onEach { Log.d("TEST", "Running app search for query: $it") }
                .collect()
        }
    }
}

