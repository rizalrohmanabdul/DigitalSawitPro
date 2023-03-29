package com.rizal.digitalsawitpro.data.repository

import com.rizal.digitalsawitpro.data.model.DataResult

interface Repository {

    fun requestGoogleApi(): String
    suspend fun saveData(data: DataResult): Boolean
}