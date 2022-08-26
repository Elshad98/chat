package com.example.chat.ui.messages

import android.os.Bundle
import com.example.chat.ui.App
import com.example.chat.ui.core.BaseActivity
import com.example.chat.ui.core.BaseFragment

class MessagesActivity : BaseActivity() {

    override var fragment: BaseFragment = MessagesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }
}