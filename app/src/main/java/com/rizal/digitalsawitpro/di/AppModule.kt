package com.rizal.digitalsawitpro.di

import android.content.Context
import android.content.SharedPreferences
import com.rizal.digitalsawitpro.utils.AppPreferences
import com.rizal.digitalsawitpro.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constant.APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun providePreferenceManager(sharedPreferences: SharedPreferences): AppPreferences {
        return AppPreferences(sharedPreferences)
    }
}