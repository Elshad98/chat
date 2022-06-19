package com.example.chat.ui.core

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
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
import javax.inject.Inject
import kotlinx.android.synthetic.main.toolbar.toolbar
import kotlinx.android.synthetic.main.toolbar.toolbar_progress_bar

abstract class BaseActivity : AppCompatActivity() {

    abstract var fragment: BaseFragment

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigator: Navigator

    @LayoutRes
    protected open val contentId = R.layout.activity_layout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentId)

        setSupportActionBar(toolbar)
        addFragment(savedInstanceState)
    }

    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(R.id.fragment_container) as BaseFragment).onBackPressed()
        super.onBackPressed()
    }

    protected open fun handleFailure(failure: Failure?) {
        hideProgress()
        when (failure) {
            is Failure.AuthError -> longToast(R.string.error_auth)
            is Failure.ServerError -> longToast(R.string.error_server)
            is Failure.TokenError -> navigator.showLogin(context = this)
            is Failure.NetworkConnectionError -> longToast(R.string.error_network)
            is Failure.AlreadyFriendError -> longToast(R.string.error_already_friend)
            is Failure.EmailAlreadyExistError -> longToast(R.string.error_email_already_exist)
            is Failure.AlreadyRequestedFriendError -> longToast(R.string.error_already_requested_friend)
            else -> Unit
        }
    }

    fun replaceFragment(fragment: BaseFragment) {
        this.fragment = fragment
        supportFragmentManager.inTransaction {
            replace(R.id.fragment_container, fragment)
        }
    }

    fun showProgress() = progressStatus(View.VISIBLE)

    fun hideProgress() = progressStatus(View.GONE)

    fun hideSoftKeyboard() {
        currentFocus?.let {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
        return ViewModelProviders
            .of(this, viewModelFactory)
            .get(viewModelClass)
    }

    private fun progressStatus(viewStatus: Int) {
        toolbar_progress_bar.visibility = viewStatus
    }

    private fun addFragment(savedInstanceState: Bundle?, fragment: BaseFragment = this.fragment) {
        savedInstanceState ?: supportFragmentManager.inTransaction {
            add(R.id.fragment_container, fragment)
        }
    }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction): Int =
    beginTransaction().func().commit()

inline fun Activity?.base(block: BaseActivity.() -> Unit) {
    (this as? BaseActivity)?.let(block)
}