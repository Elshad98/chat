package com.example.chat.presentation.settings

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.chat.R
import com.example.chat.core.extension.showToast
import com.example.chat.core.extension.supportActionBar
import com.example.chat.di.ViewModelFactory
import com.example.chat.presentation.App
import javax.inject.Inject

class SettingsFragment : PreferenceFragmentCompat() {

    companion object {

        private const val PREFERENCE_EMAIL = "email"
        private const val PREFERENCE_USERNAME = "username"
        private const val PREFERENCE_PASSWORD = "password"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: SettingsViewModel by viewModels(factoryProducer = { viewModelFactory })

    private var emailPreference: Preference? = null
    private var usernamePreference: Preference? = null
    private var passwordPreference: Preference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        setHasOptionsMenu(true)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.general_preferences, rootKey)
        emailPreference = findPreference(PREFERENCE_EMAIL)
        passwordPreference = findPreference(PREFERENCE_PASSWORD)
        usernamePreference = findPreference(PREFERENCE_USERNAME)
        setupPreferenceClickListeners()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.update_status -> {
                launchUpdateStatusFragment()
                true
            }
            R.id.set_profile_photo -> {
                showToast("Set profile photo")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeViewModel() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            emailPreference?.summary = user.email
            usernamePreference?.summary = user.name
        }
    }

    private fun setupPreferenceClickListeners() {
        usernamePreference?.setOnPreferenceClickListener {
            launchChangeUsernameFragment()
            true
        }
        passwordPreference?.setOnPreferenceClickListener {
            launchChangePasswordFragment()
            true
        }
        emailPreference?.setOnPreferenceClickListener {
            launchChangeEmailFragment()
            true
        }
    }

    private fun setupToolbar() {
        supportActionBar?.run {
            show()
            setDisplayShowTitleEnabled(true)
        }
    }

    private fun launchChangePasswordFragment() {
        findNavController().navigate(R.id.action_settingsFragment_to_changePasswordFragment)
    }

    private fun launchChangeUsernameFragment() {
        findNavController().navigate(R.id.action_settingsFragment_to_changeUsernameFragment)
    }

    private fun launchUpdateStatusFragment() {
        findNavController().navigate(R.id.action_settingsFragment_to_updateStatusFragment)
    }

    private fun launchChangeEmailFragment() {
        findNavController().navigate(R.id.action_settingsFragment_to_changeEmailFragment)
    }
}