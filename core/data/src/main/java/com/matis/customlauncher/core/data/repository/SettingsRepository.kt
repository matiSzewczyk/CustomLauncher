package com.matis.customlauncher.core.data.repository

import com.matis.customlauncher.model.LayoutType
import com.matis.customlauncher.model.MainPage
import com.matis.customlauncher.model.PageLayoutChangeResultDto
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun insertLayoutConfig(result: PageLayoutChangeResultDto)
    suspend fun getLayoutForPage(page: MainPage): Flow<LayoutType>
}
