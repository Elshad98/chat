package com.example.chat.ui.user

import android.os.Bundle
import com.example.chat.ui.App
import com.example.chat.ui.core.BaseActivity
import com.example.chat.ui.core.BaseFragment

class UserActivity : BaseActivity() {

    override var fragment: BaseFragment = UserFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }
}