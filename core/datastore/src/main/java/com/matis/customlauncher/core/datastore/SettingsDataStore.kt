package com.matis.customlauncher.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.matis.customlauncher.model.domain.HomePageLayoutType
import com.matis.customlauncher.model.domain.MainPage
import com.matis.customlauncher.model.domain.PageLayoutDto
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsDataStore @Inject constructor(
    private val datastore: DataStore<Preferences>
) {

    suspend fun updateLayoutConfig(resultDto: PageLayoutDto) {
        datastore.edit {
            it[stringPreferencesKey(resultDto.page.name)] = resultDto.layoutType.name
        }
    }

    fun getLayoutForPage(page: MainPage): Flow<HomePageLayoutType> =
        datastore.data.map {
            it[stringPreferencesKey(page.name)]
                ?.let { HomePageLayoutType.valueOf(it) }
                ?: page.defaultLayout
        }
}
