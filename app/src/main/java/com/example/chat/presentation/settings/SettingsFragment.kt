package com.example.chat.presentation.settings

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.chat.R
import com.example.chat.core.extension.supportActionBar
import com.example.chat.presentation.settings.email.ChangeEmailFragment
import com.example.chat.presentation.settings.status.UpdateStatusFragment

class SettingsFragment : PreferenceFragmentCompat(), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

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
            UpdateStatusFragment::class.java.name -> {
                launchUpdateStatusFragment()
                true
            }
            ChangeEmailFragment::class.java.name -> {
                launchChangeEmailFragment()
                true
            }
            else -> false
        }
    }

    private fun setupToolbar() {
        supportActionBar?.run {
            show()
            setDisplayShowTitleEnabled(true)
        }
    }

    private fun launchChangeEmailFragment() {
        findNavController().navigate(R.id.action_settingsFragment_to_changeEmailFragment)
    }

    private fun launchUpdateStatusFragment() {
        findNavController().navigate(R.id.action_settingsFragment_to_updateStatusFragment)
    }
}