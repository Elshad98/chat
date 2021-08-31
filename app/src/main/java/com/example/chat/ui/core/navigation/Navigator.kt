package com.example.chat.ui.core.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.example.chat.R
import com.example.chat.presentation.Authenticator
import com.example.chat.ui.home.HomeActivity
import com.example.chat.ui.login.LoginActivity
import com.example.chat.ui.register.RegisterActivity
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

    fun showEmailInvite(context: Context, email: String) {
        val appPackageName = context.packageName
        val emailIntent = Intent().apply {
            action = Intent.ACTION_SENDTO
            data = Uri.fromParts("mailto", email, null)
            putExtra(
                Intent.EXTRA_SUBJECT,
                context.resources.getString(R.string.message_subject_promt_app)
            )
            putExtra(
                Intent.EXTRA_TEXT,
                context.resources.getString(R.string.message_text_promt_app) +
                    " " + context.resources.getString(R.string.url_google_play) + appPackageName
            )
        }
        context.startActivity(Intent.createChooser(emailIntent, "Отправить"))
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