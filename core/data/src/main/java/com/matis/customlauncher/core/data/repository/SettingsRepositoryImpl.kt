package com.matis.customlauncher.core.data.repository

import com.matis.customlauncher.core.datastore.SettingsDataStore
import com.matis.customlauncher.model.LayoutType
import com.matis.customlauncher.model.MainPage
import com.matis.customlauncher.model.PageLayoutDto
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class SettingsRepositoryImpl @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : SettingsRepository {

    override suspend fun insertLayoutConfig(result: PageLayoutDto): Unit =
        withContext(Dispatchers.IO) { settingsDataStore.updateLayoutConfig(result) }

    override suspend fun getLayoutForPage(page: MainPage): Flow<LayoutType> =
        withContext(Dispatchers.IO) { settingsDataStore.getLayoutForPage(page) }
}
