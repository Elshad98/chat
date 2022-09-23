package com.example.chat.presentation.messages

import android.os.Bundle
import com.example.chat.presentation.App
import com.example.chat.presentation.core.BaseActivity
import com.example.chat.presentation.core.BaseFragment

class MessagesActivity : BaseActivity() {

    override var fragment: BaseFragment = MessagesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }
}