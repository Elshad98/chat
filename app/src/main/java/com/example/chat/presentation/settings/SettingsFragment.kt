package com.example.chat.presentation.settings

import android.Manifest
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.result.contract.ActivityResultContracts.TakePicture
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.chat.R
import com.example.chat.core.exception.Failure
import com.example.chat.core.extension.arePermissionsGranted
import com.example.chat.core.extension.isPermissionGranted
import com.example.chat.core.extension.openSystemSettings
import com.example.chat.core.extension.showToast
import com.example.chat.core.extension.supportActionBar
import com.example.chat.di.ViewModelFactory
import com.example.chat.domain.user.User
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

    private val contentLauncher = registerForActivityResult(GetContent()) { uri ->
        viewModel.onImagePicked(uri)
    }

    private val readStoragePermissionLauncher = registerForActivityResult(RequestPermission()) { isGranted ->
        if (isGranted) {
            contentLauncher.launch("image/*")
        } else {
            showWriteStoragePermissionDeniedDialog()
        }
    }

    private val cameraPermissionLauncher = registerForActivityResult(RequestMultiplePermissions()) { grantResults ->
        if (grantResults.values.all { it }) {
            viewModel.createCameraFile()
        } else {
            showCameraPermissionDeniedDialog()
        }
    }

    private val takePictureLauncher = registerForActivityResult(TakePicture()) { isSuccess ->
        if (isSuccess) {
            viewModel.changeProfilePhoto()
        }
    }

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

    override fun onStart() {
        super.onStart()
        viewModel.getUser()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_settings, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_update_status -> {
                launchUpdateStatusFragment()
                true
            }
            R.id.action_set_profile_photo -> {
                showImagePickDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showImagePickDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.photo)
            .setItems(R.array.photo_picker_items) { _, which ->
                when (which) {
                    0 -> {
                        if (requireContext().isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            contentLauncher.launch("image/*")
                        } else {
                            readStoragePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        }
                    }
                    1 -> {
                        val permissions = arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                        if (requireContext().arePermissionsGranted(*permissions)) {
                            viewModel.createCameraFile()
                        } else {
                            cameraPermissionLauncher.launch(permissions)
                        }
                    }
                }
            }
            .show()
    }

    private fun showWriteStoragePermissionDeniedDialog() {
        showPermissionDeniedDialog(R.string.write_external_storage_permission_dialog_message)
    }

    private fun showCameraPermissionDeniedDialog() {
        showPermissionDeniedDialog(R.string.camera_permission_dialog_message)
    }

    private fun showPermissionDeniedDialog(@StringRes messageId: Int) {
        AlertDialog.Builder(requireContext())
            .setMessage(messageId)
            .setNegativeButton(R.string.permission_dialog_cancel, null)
            .setPositiveButton(R.string.permission_dialog_ok) { _, _ ->
                requireContext().openSystemSettings()
            }
            .show()
    }

    private fun observeViewModel() {
        viewModel.user.observe(viewLifecycleOwner, ::handleUser)
        viewModel.failure.observe(viewLifecycleOwner, ::handleFailure)
        viewModel.cameraFile.observe(viewLifecycleOwner, takePictureLauncher::launch)
        viewModel.updateProfileSuccess.observe(viewLifecycleOwner) {
            showToast(R.string.success_edit_user)
        }
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.FilePickError -> R.string.error_picking_file
            else -> R.string.error_server
        }.let(::showToast)
    }

    private fun handleUser(user: User) {
        emailPreference?.summary = user.email
        usernamePreference?.summary = user.name
        passwordPreference?.summary = "*".repeat(user.password.length)
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