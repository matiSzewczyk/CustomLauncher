package com.matis.customlauncher.core.data.di

import com.matis.customlauncher.core.data.repository.HomeScreenRepository
import com.matis.customlauncher.core.data.repository.HomeScreenRepositoryImpl
import com.matis.customlauncher.core.data.repository.InstalledApplicationsRepository
import com.matis.customlauncher.core.data.repository.InstalledApplicationsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsHomeScreenRepository(
        impl: HomeScreenRepositoryImpl
    ): HomeScreenRepository

    @Binds
    internal abstract fun bindsPackagesRepository(
        impl: InstalledApplicationsRepositoryImpl
    ): InstalledApplicationsRepository
}
