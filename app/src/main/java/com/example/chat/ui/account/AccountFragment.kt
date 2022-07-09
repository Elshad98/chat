package com.example.chat.ui.account

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.chat.R
import com.example.chat.domain.account.AccountEntity
import com.example.chat.extensions.longToast
import com.example.chat.extensions.toggleVisibility
import com.example.chat.presentation.viewmodel.AccountViewModel
import com.example.chat.presentation.viewmodel.MediaViewModel
import com.example.chat.ui.App
import com.example.chat.ui.core.BaseFragment
import com.example.chat.ui.core.GlideHelper
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_account
    override val titleToolbar = R.string.screen_account

    lateinit var mediaViewModel: MediaViewModel
    lateinit var accountViewModel: AccountViewModel

    var accountEntity: AccountEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        mediaViewModel = viewModel(MediaViewModel::class.java)
        accountViewModel = viewModel(AccountViewModel::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mediaViewModel.onPickImageResult(requestCode, resultCode, data)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accountViewModel.accountData.observe(viewLifecycleOwner, Observer(::handleAccount))
        accountViewModel.editAccountData.observe(
            viewLifecycleOwner,
            Observer(::handleEditingAccount)
        )
        mediaViewModel.cameraFileCreatedData.observe(
            viewLifecycleOwner,
            Observer(::onCameraFileCreated)
        )
        mediaViewModel.pickedImageData.observe(viewLifecycleOwner, Observer(::onImagePicked))
        mediaViewModel.progressData.observe(viewLifecycleOwner, Observer(::updateProgress))
        accountViewModel.failureData.observe(
            viewLifecycleOwner,
            Observer { it.getContentIfNotHandled()?.let(::handleFailure) }
        )
        mediaViewModel.failureData.observe(
            viewLifecycleOwner,
            Observer { it.getContentIfNotHandled()?.let(::handleFailure) }
        )

        showProgress()
        accountViewModel.getAccount()
        account_btn_edit.setOnClickListener {
            view.clearFocus()
            val fieldsValid = validateFields()
            if (!fieldsValid) {
                return@setOnClickListener
            }

            val passwordsValid = validatePasswords()
            if (!passwordsValid) {
                return@setOnClickListener
            }

            showProgress()

            accountEntity?.run {
                name = account_input_name.text.toString()
                email = account_input_email.text.toString()
                status = account_input_status.text.toString()

                val newPassword = account_input_new_password.text.toString()
                if (newPassword.isNotEmpty()) {
                    password = newPassword
                }

                accountViewModel.editAccount(this)
            }
        }
        user_img_photo.setOnClickListener {
            base {
                navigator.showPickFromDialog(this) { fromCamera ->
                    if (fromCamera) {
                        mediaViewModel.createCameraFile()
                    } else {
                        navigator.showGallery(this)
                    }
                }
            }
        }
    }

    private fun validatePasswords(): Boolean {
        val currentPassword = account_input_current_password.text.toString()
        val newPassword = account_input_new_password.text.toString()

        return when {
            currentPassword.isNotEmpty() && newPassword.isNotEmpty() -> {
                if (currentPassword == accountEntity?.password) {
                    true
                } else {
                    requireContext().longToast(getString(R.string.error_wrong_password))
                    false
                }
            }
            currentPassword.isEmpty() && newPassword.isEmpty() -> true
            else -> {
                requireContext().longToast(getString(R.string.error_empty_password))
                false
            }
        }
    }

    private fun validateFields(): Boolean {
        hideSoftKeyboard()
        val allFields = arrayOf(account_input_email, account_input_name)
        var allValid = true
        for (field in allFields) {
            allValid = field.testValidity() && allValid
        }
        return allValid
    }

    private fun handleAccount(account: AccountEntity?) {
        hideProgress()
        accountEntity = account
        account?.let {
            GlideHelper.loadImage(requireActivity(), it.image, user_img_photo)
            account_input_email.setText(it.email)
            account_input_name.setText(it.name)
            account_input_status.setText(it.status)

            account_input_current_password.setText("")
            account_input_new_password.setText("")
        }
    }

    private fun onCameraFileCreated(uri: Uri?) {
        base {
            if (uri != null) {
                navigator.showCamera(this, uri)
            }
        }
    }

    private fun onImagePicked(pickedImage: MediaViewModel.PickedImage?) {
        if (pickedImage != null) {
            accountEntity?.image = pickedImage.string

            val placeholder = user_img_photo.drawable
            Glide.with(this)
                .load(pickedImage.bitmap)
                .placeholder(placeholder)
                .error(placeholder)
                .into(user_img_photo)
        }
    }

    private fun handleEditingAccount(account: AccountEntity?) {
        requireContext().longToast(getString(R.string.success_edit_user))
        accountViewModel.getAccount()
    }

    private fun updateProgress(progress: Boolean?) {
        account_group_progress.toggleVisibility(progress == true)
    }
}