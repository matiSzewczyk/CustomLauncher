package com.matis.customlauncher.domain.settings

import com.matis.customlauncher.core.data.repository.SettingsRepository
import com.matis.customlauncher.model.domain.MainPage
import com.matis.customlauncher.model.domain.PageLayoutDto
import javax.inject.Inject

class SaveNewLayoutConfig @Inject constructor(
    private val repository: SettingsRepository,
    private val reconfigureHomeScreenLayout: ReconfigureHomeScreenLayout
) {

    suspend operator fun invoke(result: PageLayoutDto) {
        if (result.page == MainPage.HOME) reconfigureHomeScreenLayout(result)
        repository.insertLayoutConfig(result)
    }
}
