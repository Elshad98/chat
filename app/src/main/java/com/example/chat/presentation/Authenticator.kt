package com.example.chat.presentation

import com.example.chat.cache.SharedPrefsManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Authenticator @Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager
) {

    fun userLoggedIn(): Boolean = sharedPrefsManager.containsAnyAccount()
}