package com.matis.core.customlauncher.database.di

import android.content.Context
import androidx.room.Room
import com.matis.core.customlauncher.database.LauncherRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabaseInstance(
        @ApplicationContext context: Context
    ): LauncherRoomDatabase =
        Room
            .databaseBuilder(
                context = context,
                klass = LauncherRoomDatabase::class.java,
                name = "launcher_database"
            )
            .fallbackToDestructiveMigration(true)
            .build()
}
