package com.example.chat.ui.core.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.chat.presentation.Authenticator
import com.example.chat.ui.register.RegisterActivity
import com.example.chat.ui.home.HomeActivity
import com.example.chat.ui.login.LoginActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor(
    private val authenticator: Authenticator
) {

    fun showMain(context: Context) {
        if (authenticator.userLoggedIn()) {
            showHome(context, false)
        } else {
            showLogin(context, false)
        }
    }

    fun showLogin(context: Context, newTask: Boolean = true) {
        context.startActivity<LoginActivity>(newTask)
    }

    fun showHome(context: Context, newTask: Boolean = true) {
        context.startActivity<HomeActivity>(newTask)
    }

    fun showSignUp(context: Context) {
        context.startActivity<RegisterActivity>()
    }
}

private inline fun <reified T> Context.startActivity(
    newTask: Boolean = false,
    args: Bundle? = null
) {
    val intent = Intent(this, T::class.java).apply {
        if (newTask) {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        putExtra("args", args)
    }
    this.startActivity(intent)
}