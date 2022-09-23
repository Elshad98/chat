package com.example.chat.presentation.account

import android.os.Bundle
import com.example.chat.presentation.App
import com.example.chat.presentation.core.BaseActivity
import com.example.chat.presentation.core.BaseFragment

class AccountActivity : BaseActivity() {

    override var fragment: BaseFragment = AccountFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }
}