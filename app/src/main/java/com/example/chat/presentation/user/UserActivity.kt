package com.example.chat.presentation.user

import android.os.Bundle
import com.example.chat.presentation.App
import com.example.chat.presentation.core.BaseActivity
import com.example.chat.presentation.core.BaseFragment

class UserActivity : BaseActivity() {

    override var fragment: BaseFragment = UserFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }
}