package com.matis.customlauncher.core.data.repository

import com.matis.core.customlauncher.database.dao.HomeScreenApplicationDao
import com.matis.core.customlauncher.database.model.toDomain
import com.matis.customlauncher.core.datastore.SettingsDataStore
import com.matis.customlauncher.model.domain.ApplicationInfoDto
import com.matis.customlauncher.model.domain.HomePageLayoutType
import com.matis.customlauncher.model.domain.HomeScreenApplicationDto
import com.matis.customlauncher.model.domain.HomeScreenDto
import com.matis.customlauncher.model.domain.MainPage
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class HomeScreenRepositoryImpl @Inject constructor(
    private val dao: HomeScreenApplicationDao,
    private val dataStore: SettingsDataStore
) : HomeScreenRepository {

    override suspend fun insertHomeScreenApplication(application: ApplicationInfoDto) {
        val layout = dataStore.getLayoutForPage(MainPage.HOME).first()
        dao.insertHomeScreenApplication(application, layout)
    }

    override suspend fun reconfigureHomeScreenLayout(newLayout: HomePageLayoutType) {
        dao.migrateHomeScreenItemsForNewLayout(newLayout)
    }

    override suspend fun fetchHomeScreenLayout(): HomePageLayoutType =
        dataStore.getLayoutForPage(MainPage.HOME).first()

    override suspend fun insertNewApplicationsPage() {
        dao.insertNewHomeScreenPage()
    }

    override suspend fun removeApplicationsPage(pageIndex: Int) =
        withContext(Dispatchers.IO) { dao.removeApplicationsPage(pageIndex) }

    override fun fetchHomeScreens(): Flow<HomeScreenDto> =
        combine(
            dao.fetchAllHomeScreens(),
            dataStore.getLayoutForPage(MainPage.HOME),
            dataStore.getApplicationIconConfig()
        ) { screens, layoutType, applicationIconConfig ->
            HomeScreenDto(
                layoutType = layoutType,
                applicationIconConfig = applicationIconConfig,
                pages = screens.map { it.toDomain() }
            )
        }

    override fun fetchHomeScreenApplications(): Flow<List<HomeScreenApplicationDto>> =
        dao.fetchAllHomeScreenApplications()
            .map { it.map { it.toDomain() } }

    override fun removeApplicationFromHomeScreen(packageName: String) {
        dao.removeApplicationFromHomeScreen(packageName)
    }
}
