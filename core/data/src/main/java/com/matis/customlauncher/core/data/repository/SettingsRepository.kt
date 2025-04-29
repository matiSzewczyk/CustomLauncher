package com.matis.customlauncher.core.data.repository

import com.matis.customlauncher.model.PageLayoutChangeResultDto

interface SettingsRepository {
    suspend fun insertLayoutConfig(result: PageLayoutChangeResultDto)
}
