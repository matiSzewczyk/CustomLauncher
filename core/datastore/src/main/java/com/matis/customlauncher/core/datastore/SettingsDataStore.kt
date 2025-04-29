package com.matis.customlauncher.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.matis.customlauncher.core.datastore.SettingsDataStore.Keys.LAYOUT_TYPE
import com.matis.customlauncher.core.datastore.SettingsDataStore.Keys.PAGE
import com.matis.customlauncher.model.PageLayoutChangeResultDto
import javax.inject.Inject

class SettingsDataStore @Inject constructor(
    private val datastore: DataStore<Preferences>
) {

    suspend fun updateLayoutConfig(
        resultDto: PageLayoutChangeResultDto
    ) {
        datastore.edit {
            it[PAGE] = resultDto.page.name
            it[LAYOUT_TYPE] = resultDto.layoutType.name
        }
    }

    object Keys {
        val PAGE = stringPreferencesKey("page")
        val LAYOUT_TYPE = stringPreferencesKey("layout_type")
    }
}
