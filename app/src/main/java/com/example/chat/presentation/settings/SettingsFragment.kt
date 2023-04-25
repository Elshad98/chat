package com.example.chat.presentation.settings

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.chat.R
import com.example.chat.core.extension.showToast
import com.example.chat.core.extension.supportActionBar
import com.example.chat.presentation.settings.email.ChangeEmailFragment
import com.example.chat.presentation.settings.password.ChangePasswordFragment
import com.example.chat.presentation.settings.username.ChangeUsernameFragment

class SettingsFragment : PreferenceFragmentCompat(), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.general_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat,
        pref: Preference
    ): Boolean {
        return when (pref.fragment) {
            ChangeUsernameFragment::class.java.name -> {
                launchChangeUsernameFragment()
                true
            }
            ChangeEmailFragment::class.java.name -> {
                launchChangeEmailFragment()
                true
            }
            ChangePasswordFragment::class.java.name -> {
                launchChangePasswordFragment()
                true
            }
            else -> false
        }
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