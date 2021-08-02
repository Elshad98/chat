package com.example.chat.ui.activity

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chat.R
import com.example.chat.cache.AccountCacheImpl
import com.example.chat.cache.SharedPrefsManager
import com.example.chat.data.account.AccountRepositoryImpl
import com.example.chat.domain.account.AccountRepository
import com.example.chat.domain.account.Register
import com.example.chat.remote.account.AccountRemoteImpl
import com.example.chat.remote.core.NetworkHandler
import com.example.chat.remote.core.Request
import com.example.chat.remote.service.ServiceFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPrefs = this.getSharedPreferences(this.packageName, Context.MODE_PRIVATE)

        val accountCache = AccountCacheImpl(SharedPrefsManager(sharedPrefs))
        val accountRemote = AccountRemoteImpl(
            Request(NetworkHandler(this)),
            ServiceFactory.makeService(true)
        )

        val accountRepository: AccountRepository = AccountRepositoryImpl(
            accountRemote,
            accountCache
        )

        accountCache.saveToken("12345")

        val register = Register(accountRepository)
        register(Register.Params("abcd.efgh@m.com", "test", "qwerty")) {
            it.either(
                {
                    Toast.makeText(this, it.javaClass.simpleName, Toast.LENGTH_SHORT).show()
                },
                {
                    Toast.makeText(this, "Аккаунт создан", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}