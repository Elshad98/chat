package com.example.chat.ui.home

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import com.example.chat.R
import com.example.chat.domain.account.AccountEntity
import com.example.chat.domain.type.None
import com.example.chat.presentation.viewmodel.AccountViewModel
import com.example.chat.ui.App
import com.example.chat.ui.core.BaseActivity
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.navigation.*

class HomeActivity : BaseActivity() {

    override val fragment = ChatsFragment()

    override val contentId = R.layout.activity_navigation

    private lateinit var accountViewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.appComponent.inject(this)

        accountViewModel = viewModel(AccountViewModel::class.java)
        accountViewModel.accountData.observe(this, Observer(::handleAccount))
        accountViewModel.logoutData.observe(this, Observer(::handleLogout))
        accountViewModel.failureData.observe(
            this,
            Observer { it.getContentIfNotHandled()?.let(::handleFailure) }
        )

        accountViewModel.getAccount()

        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigation_btn_logout.setOnClickListener {
            accountViewModel.logout()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (drawer_layout.isDrawerOpen(navigation_view)) {
                    drawer_layout.closeDrawer(navigation_view)
                } else {
                    drawer_layout.openDrawer(navigation_view)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun handleAccount(accountEntity: AccountEntity?) {
        accountEntity?.let {
            navigation_label_user_name.text = it.name
            navigation_label_user_email.text = it.email
            navigation_label_user_status.text = it.status

            navigation_label_user_status.visibility = if (it.status.isNotEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun handleLogout(none: None?) {
        navigator.showLogin(this)
        finish()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(navigation_view)) {
            drawer_layout.closeDrawer(navigation_view)
        } else {
            super.onBackPressed()
        }
    }
}