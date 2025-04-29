package com.matis.customlauncher.core.data.repository

import com.matis.customlauncher.core.datastore.SettingsDataStore
import com.matis.customlauncher.model.PageLayoutChangeResultDto
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class SettingsRepositoryImpl @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : SettingsRepository {

    override suspend fun insertLayoutConfig(result: PageLayoutChangeResultDto): Unit =
        withContext(Dispatchers.IO) { settingsDataStore.updateLayoutConfig(result) }
}
