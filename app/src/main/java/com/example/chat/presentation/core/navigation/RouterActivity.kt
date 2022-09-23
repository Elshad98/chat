package com.example.chat.presentation.core.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chat.presentation.App
import javax.inject.Inject

class RouterActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.appComponent.inject(this)

        navigator.showMain(this)
    }
}