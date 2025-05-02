package com.matis.customlauncher.core.data.repository

import com.matis.customlauncher.core.datastore.SettingsDataStore
import com.matis.customlauncher.model.domain.HomePageLayoutType
import com.matis.customlauncher.model.domain.MainPage
import com.matis.customlauncher.model.domain.PageLayoutDto
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class SettingsRepositoryImpl @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : SettingsRepository {

    override suspend fun insertLayoutConfig(result: PageLayoutDto): Unit =
        withContext(Dispatchers.IO) { settingsDataStore.updateLayoutConfig(result) }

    override suspend fun getLayoutForPage(page: MainPage): Flow<HomePageLayoutType> =
        withContext(Dispatchers.IO) { settingsDataStore.getLayoutForPage(page) }
}
