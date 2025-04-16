package com.matis.customlauncher.applications.di

import com.matis.customlauncher.applications.ApplicationsApi
import com.matis.customlauncher.applications.ApplicationsApiImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationsModule {

    @Binds
    internal abstract fun bindsApplicationsApi(
        impl: ApplicationsApiImpl
    ): ApplicationsApi
}
