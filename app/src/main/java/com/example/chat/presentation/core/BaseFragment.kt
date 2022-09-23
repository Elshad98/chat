package com.example.chat.presentation.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.chat.R
import com.example.chat.domain.type.Failure
import com.example.chat.presentation.core.navigation.Navigator
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    abstract val layoutId: Int

    @StringRes
    open val titleToolbar = R.string.app_name
    open val showToolbar = true

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onResume() {
        super.onResume()

        base {
            if (showToolbar) supportActionBar?.show() else supportActionBar?.hide()
            supportActionBar?.title = getString(titleToolbar)
        }
    }

    open fun onBackPressed() = Unit

    open fun updateProgress(status: Boolean) {
        if (status) {
            showProgress()
        } else {
            hideProgress()
        }
    }

    inline fun base(block: BaseActivity.() -> Unit) {
        activity.base(block)
    }

    fun showProgress() = base { showProgress() }

    fun hideProgress() = base { hideProgress() }

    fun handleFailure(failure: Failure?) = base { handleFailure(failure) }

    fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
        return ViewModelProviders
            .of(this, viewModelFactory)
            .get(viewModelClass)
    }

    fun close() {
        fragmentManager?.popBackStack()
    }
}