package com.rizal.digitalsawitpro.data.repository

import com.rizal.digitalsawitpro.utils.AppPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideRepositoryImpl(
        appPreferences: AppPreferences
    ): Repository {
        return RepositoryImpl(
            appPreferences
        )
    }
}