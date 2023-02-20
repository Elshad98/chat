package com.example.chat.presentation.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.chat.R
import com.example.chat.core.extension.supportActionBar

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.general_preferences, rootKey)
        supportActionBar?.show()
    }
}