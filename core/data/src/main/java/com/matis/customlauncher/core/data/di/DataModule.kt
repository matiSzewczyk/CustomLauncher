package com.matis.customlauncher.core.data.di

import com.matis.customlauncher.core.data.repository.EnvironmentRepository
import com.matis.customlauncher.core.data.repository.EnvironmentRepositoryImpl
import com.matis.customlauncher.core.data.repository.HomeScreenRepository
import com.matis.customlauncher.core.data.repository.HomeScreenRepositoryImpl
import com.matis.customlauncher.core.data.repository.InstalledApplicationsRepository
import com.matis.customlauncher.core.data.repository.InstalledApplicationsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsHomeScreenRepository(
        impl: HomeScreenRepositoryImpl
    ): HomeScreenRepository

    @Singleton
    @Binds
    internal abstract fun bindsPackagesRepository(
        impl: InstalledApplicationsRepositoryImpl
    ): InstalledApplicationsRepository

    @Singleton
    @Binds
    internal abstract fun bindsEnvironmentRepository(
        impl: EnvironmentRepositoryImpl
    ): EnvironmentRepository
}
