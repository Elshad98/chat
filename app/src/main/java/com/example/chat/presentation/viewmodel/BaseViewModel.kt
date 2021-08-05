package com.example.chat.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.domain.type.HandleOnce
import com.example.chat.domain.type.exception.Failure

open class BaseViewModel : ViewModel() {

    var failureData: MutableLiveData<HandleOnce<Failure>> = MutableLiveData()

    protected fun handleFailure(failure: Failure) {
        failureData.value = HandleOnce(failure)
    }
}