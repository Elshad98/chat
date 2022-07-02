package com.example.chat.ui.account

import android.os.Bundle
import com.example.chat.ui.App
import com.example.chat.ui.core.BaseActivity
import com.example.chat.ui.core.BaseFragment

class AccountActivity : BaseActivity() {

    override var fragment: BaseFragment = AccountFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }
}