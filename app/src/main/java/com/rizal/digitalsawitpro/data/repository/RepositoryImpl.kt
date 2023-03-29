package com.rizal.digitalsawitpro.data.repository

import com.rizal.digitalsawitpro.utils.AppPreferences
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val appPreferences: AppPreferences,
): Repository {

    override fun requestGoogleApi(): String {
        return appPreferences.googleApi
    }
}
