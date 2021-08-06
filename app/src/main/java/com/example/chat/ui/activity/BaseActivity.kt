package com.example.chat.ui.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.chat.R
import com.example.chat.domain.type.exception.Failure
import com.example.chat.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    abstract val fragment: BaseFragment

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)

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
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    fun handleFailure(failure: Failure?) {
        hideProgress()
        when (failure) {
            is Failure.NetworkConnectionError -> showMessage(getString(R.string.error_network))
            is Failure.ServerError -> showMessage(getString(R.string.error_server))
            is Failure.EmailAlreadyExistError -> showMessage(getString(R.string.error_email_already_exist))
        }
    }

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    inline fun <reified T : ViewModel> viewModel(body: T.() -> Unit): T {
        val viewModel = ViewModelProviders.of(this, viewModelFactory)[T::class.java]
        viewModel.body()
        return viewModel
    }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction().func().commit()

inline fun Activity?.base(block: BaseActivity.() -> Unit) {
    (this as? BaseActivity)?.let(block)
}