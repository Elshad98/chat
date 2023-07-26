package com.example.chat.core.platform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.core.exception.Failure

abstract class BaseViewModel : ViewModel() {

    private val _failure = MutableLiveData<Failure>()
    val failure: LiveData<Failure> = _failure

    protected fun handleFailure(failure: Failure) {
        _failure.value = failure
    }
}