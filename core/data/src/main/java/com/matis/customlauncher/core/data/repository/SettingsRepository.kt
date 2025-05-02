package com.matis.customlauncher.core.data.repository

import com.matis.customlauncher.model.domain.HomePageLayoutType
import com.matis.customlauncher.model.domain.MainPage
import com.matis.customlauncher.model.domain.PageLayoutDto
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun insertLayoutConfig(result: PageLayoutDto)
    suspend fun getLayoutForPage(page: MainPage): Flow<HomePageLayoutType>
}
