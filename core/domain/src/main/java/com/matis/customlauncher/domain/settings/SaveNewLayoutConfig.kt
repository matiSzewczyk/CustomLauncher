package com.matis.customlauncher.domain.settings

import com.matis.customlauncher.core.data.repository.SettingsRepository
import com.matis.customlauncher.model.PageLayoutChangeResultDto
import javax.inject.Inject

class SaveNewLayoutConfig @Inject constructor(
    private val repository: SettingsRepository
) {

    suspend operator fun invoke(result: PageLayoutChangeResultDto) {
        repository.insertLayoutConfig(result)
    }
}
