package com.rizal.digitalsawitpro.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(

): ViewModel() {

    private val _load = MutableLiveData<Boolean>()
    val load: LiveData<Boolean> = _load


    fun loadSplash(){
        viewModelScope.launch {
            delay(2000L)
            _load.postValue(true)
        }
    }
}