package com.matis.customlauncher.core.data.repository

import com.matis.customlauncher.model.LayoutType
import com.matis.customlauncher.model.MainPage
import com.matis.customlauncher.model.PageLayoutDto
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun insertLayoutConfig(result: PageLayoutDto)
    suspend fun getLayoutForPage(page: MainPage): Flow<LayoutType>
}
