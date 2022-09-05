package com.example.chat.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.domain.type.Failure

abstract class BaseViewModel : ViewModel() {

    val failureData: MutableLiveData<Failure> = MutableLiveData()
    val progressData: MutableLiveData<Boolean> = MutableLiveData()

    protected fun handleFailure(failure: Failure) {
        failureData.value = failure
        updateProgress(false)
    }

    protected fun updateProgress(progress: Boolean) {
        progressData.value = progress
    }
}