package com.matis.core.customlauncher.database.di

import com.matis.core.customlauncher.database.LauncherRoomDatabase
import com.matis.core.customlauncher.database.dao.HomeScreenApplicationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {

    @Provides
    fun provideHomeScreenApplicationDao(
        database: LauncherRoomDatabase
    ): HomeScreenApplicationDao =
        database.getHomeScreenApplicationDao()
}
