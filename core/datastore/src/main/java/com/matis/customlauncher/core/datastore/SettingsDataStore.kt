package com.matis.customlauncher.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.matis.customlauncher.model.domain.ApplicationIconConfigDto
import com.matis.customlauncher.model.domain.ApplicationIconConfigDto.Companion.defaults
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

    suspend fun updateApplicationIconConfig(config: ApplicationIconConfigDto) {
        datastore.edit {
            it[stringPreferencesKey(ICON_SHAPE_KEY)] = config.iconShape
            it[booleanPreferencesKey(SHOW_LABEL_KEY)] = config.showLabel
        }
    }

    fun getLayoutForPage(page: MainPage): Flow<HomePageLayoutType> =
        datastore.data.map {
            it[stringPreferencesKey(page.name)]
                ?.let { HomePageLayoutType.valueOf(it) }
                ?: page.defaultLayout
        }

    fun getApplicationIconConfig(): Flow<ApplicationIconConfigDto> =
        datastore.data.map {
            ApplicationIconConfigDto(
                showLabel = it[booleanPreferencesKey(SHOW_LABEL_KEY)] ?: defaults.showLabel,
                iconShape = it[stringPreferencesKey(ICON_SHAPE_KEY)] ?: defaults.iconShape
            )
        }

    companion object {
        private const val SHOW_LABEL_KEY = "show_label"
        private const val ICON_SHAPE_KEY = "icon_shape"
    }
}
