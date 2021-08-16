package com.example.chat.ui.core

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.chat.R
import com.example.chat.domain.type.Failure
import com.example.chat.extensions.longToast
import com.example.chat.ui.core.navigation.Navigator
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    abstract val fragment: BaseFragment

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigator: Navigator

    open val contentId = R.layout.activity_layout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentId)

        setSupportActionBar(toolbar)
        addFragment(savedInstanceState)
    }

    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(R.id.fragmentContainer) as BaseFragment).onBackPressed()
        super.onBackPressed()
    }

    fun addFragment(savedInstanceState: Bundle?) {
        savedInstanceState ?: supportFragmentManager.inTransaction {
            add(R.id.fragmentContainer, fragment)
        }
    }

    fun showProgress() = progressStatus(View.VISIBLE)

    fun hideProgress() = progressStatus(View.GONE)

    fun progressStatus(viewStatus: Int) {
        toolbar_progress_bar.visibility = viewStatus
    }

    fun hideSoftKeyboard() {
        currentFocus?.let {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    fun handleFailure(failure: Failure?) {
        hideProgress()
        when (failure) {
            is Failure.NetworkConnectionError -> longToast(R.string.error_network)
            is Failure.ServerError -> longToast(R.string.error_server)
            is Failure.EmailAlreadyExistError -> longToast(R.string.error_email_already_exist)
            is Failure.AuthError -> longToast(R.string.error_auth)
            is Failure.TokenError -> navigator.showLogin(this)
        }
    }

    fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
        return ViewModelProviders
            .of(this, viewModelFactory)
            .get(viewModelClass)
    }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction): Int =
    beginTransaction().func().commit()

inline fun Activity?.base(block: BaseActivity.() -> Unit) {
    (this as? BaseActivity)?.let(block)
}