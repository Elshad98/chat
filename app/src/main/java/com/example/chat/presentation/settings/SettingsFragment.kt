package com.example.chat.presentation.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chat.R
import com.example.chat.core.extension.supportActionBar
import com.example.chat.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding by viewBinding(FragmentSettingsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        supportActionBar?.show()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        with(binding) {
            textChangeStatus.setOnClickListener { }
        }
    }
}