package pl.matis.customlauncher.common

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class DispatcherModule {

    @Binds
    internal abstract fun bindDispatcherProvider(
        impl: DispatcherProviderImpl
    ): DispatcherProvider
}
