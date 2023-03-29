package com.rizal.digitalsawitpro.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizal.digitalsawitpro.data.model.DataResult
import com.rizal.digitalsawitpro.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _data = MutableLiveData<DataResult>()
    val data: LiveData<DataResult> = _data

    private val _onSave = MutableLiveData<Boolean>()
    val onSave: LiveData<Boolean> = _onSave


    fun loadGoogleApi(): String{
       return repository.requestGoogleApi()
    }

    fun save(text: String, distance: String, time: String){
        viewModelScope.launch {
            val datas = DataResult(text = text, distance = distance, time = time)
            _data.postValue(datas)
            if (repository.saveData(datas)){
                _onSave.postValue(true)
            } else {
                _onSave.postValue(false)
            }
        }
    }

}