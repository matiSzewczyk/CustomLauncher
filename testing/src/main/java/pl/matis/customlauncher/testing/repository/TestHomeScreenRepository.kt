package pl.matis.customlauncher.testing.repository

import com.matis.customlauncher.core.data.repository.HomeScreenRepository
import com.matis.customlauncher.model.domain.ApplicationIconConfigDto
import com.matis.customlauncher.model.domain.ApplicationInfoDto
import com.matis.customlauncher.model.domain.HomePageLayoutType
import com.matis.customlauncher.model.domain.HomeScreenApplicationDto
import com.matis.customlauncher.model.domain.HomeScreenDto
import com.matis.customlauncher.model.domain.HomeScreenPageDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

class TestHomeScreenRepository : HomeScreenRepository {

    override suspend fun insertHomeScreenApplication(application: ApplicationInfoDto) {
    }

    override suspend fun reconfigureHomeScreenLayout(newLayout: HomePageLayoutType) {
    }

    override suspend fun fetchHomeScreenLayout(): HomePageLayoutType =
        HomePageLayoutType.GRID_3X4

    override suspend fun insertNewApplicationsPage() {
    }

    override suspend fun removeApplicationsPage(pageIndex: Int) {
    }

    override fun fetchHomeScreens(): Flow<HomeScreenDto> =
        flowOf(
            HomeScreenDto(
                layoutType = HomePageLayoutType.GRID_3X4,
                applicationIconConfig = ApplicationIconConfigDto(
                    iconShape = "square",
                    showLabel = false
                ),
                pages = listOf(
                    HomeScreenPageDto(position = 0, applications = listOf(), folders = listOf())
                )
            )
        )

    override fun fetchHomeScreenApplications(): Flow<List<HomeScreenApplicationDto>> =
        flowOf(emptyList())

    override fun removeApplicationFromHomeScreen(packageName: String) {
    }
}
