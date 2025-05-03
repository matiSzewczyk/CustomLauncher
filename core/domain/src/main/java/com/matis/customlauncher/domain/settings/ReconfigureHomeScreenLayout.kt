package com.matis.customlauncher.domain.settings

import com.matis.customlauncher.core.data.repository.HomeScreenRepository
import com.matis.customlauncher.model.domain.HomePageLayoutType
import com.matis.customlauncher.model.domain.PageLayoutDto
import javax.inject.Inject

class ReconfigureHomeScreenLayout @Inject constructor(
    private val homeScreenRepository: HomeScreenRepository
) {

    suspend operator fun invoke(pageLayout: PageLayoutDto) {
        val oldLayout = homeScreenRepository.fetchHomeScreenLayout()
        if (pageLayout.layoutType fitsFewerItemsThan oldLayout) {
            homeScreenRepository.reconfigureHomeScreenLayout(newLayout = pageLayout.layoutType)
        }
    }

    private infix fun HomePageLayoutType.fitsFewerItemsThan(other: HomePageLayoutType): Boolean =
        appCap < other.appCap
}
