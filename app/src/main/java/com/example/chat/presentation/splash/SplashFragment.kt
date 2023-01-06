package com.example.chat.presentation.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.chat.R
import com.example.chat.core.extension.supportActionBar
import com.example.chat.presentation.App
import javax.inject.Inject

class SplashFragment : Fragment(R.layout.fragment_splash) {

    @Inject
    lateinit var authenticator: Authenticator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        supportActionBar?.hide()
        authenticator.userLoggedIn { isLoggedIn ->
            Handler(Looper.getMainLooper()).postDelayed(
                { if (isLoggedIn) launchHomeFragment() else launchLoginFragment() },
                1500
            )
        }
    }

    private fun launchLoginFragment() {
        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
    }

    private fun launchHomeFragment() {
        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
    }
}