package com.rizal.digitalsawitpro.data.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rizal.digitalsawitpro.data.model.DataResult
import com.rizal.digitalsawitpro.utils.AppPreferences
import java.util.UUID
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val appPreferences: AppPreferences,
): Repository {

    override fun requestGoogleApi(): String {
        return appPreferences.googleApi
    }

    override suspend fun saveData(data: DataResult): Boolean {
        val result = MutableLiveData<Boolean>()
        var ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("ResultScan")
        ref.child(UUID.randomUUID().toString()).setValue(data).addOnSuccessListener {
            result.postValue(true)
        }.addOnFailureListener{
            result.postValue(false)
        }
        return result.value ?: true
    }
}
