package com.example.chat.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.domain.type.Failure
import com.example.chat.domain.type.HandleOnce

abstract class BaseViewModel : ViewModel() {

    val progressData: MutableLiveData<Boolean> = MutableLiveData()
    val failureData: MutableLiveData<HandleOnce<Failure>> = MutableLiveData()

    protected fun handleFailure(failure: Failure) {
        failureData.value = HandleOnce(failure)
    }

    protected fun updateProgress(progress: Boolean) {
        progressData.value = progress
    }
}