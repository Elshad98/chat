package com.example.chat.presentation.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import toothpick.Scope
import toothpick.smoothie.viewmodel.installViewModelBinding

inline fun <reified T : ViewModel> Scope.installVMBinding(fragment: Fragment): Scope {
    val viewModelFactory = object : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return getInstance(modelClass)
        }
    }
    installViewModelBinding<T>(fragment, viewModelFactory)
    return this
}