package com.matis.customlauncher.domain.settings

import com.matis.customlauncher.core.data.repository.SettingsRepository
import com.matis.customlauncher.model.domain.ApplicationIconConfigDto
import javax.inject.Inject

class SaveNewApplicationIconConfig @Inject constructor(
    private val repository: SettingsRepository
) {

    suspend operator fun invoke(config: ApplicationIconConfigDto) {
        repository.insertApplicationIconConfig(config)
    }
}
