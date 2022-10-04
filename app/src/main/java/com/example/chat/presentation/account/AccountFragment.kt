package com.example.chat.presentation.account

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.chat.R
import com.example.chat.domain.user.User
import com.example.chat.core.extension.hideKeyboard
import com.example.chat.core.extension.showToast
import com.example.chat.core.extension.toggleVisibility
import com.example.chat.presentation.App
import com.example.chat.presentation.core.BaseFragment
import com.example.chat.presentation.core.GlideHelper
import com.example.chat.presentation.viewmodel.AccountViewModel
import com.example.chat.presentation.viewmodel.MediaViewModel
import kotlinx.android.synthetic.main.fragment_account.account_btn_edit
import kotlinx.android.synthetic.main.fragment_account.account_group_progress
import kotlinx.android.synthetic.main.fragment_account.account_input_current_password
import kotlinx.android.synthetic.main.fragment_account.account_input_email
import kotlinx.android.synthetic.main.fragment_account.account_input_name
import kotlinx.android.synthetic.main.fragment_account.account_input_new_password
import kotlinx.android.synthetic.main.fragment_account.account_input_status
import kotlinx.android.synthetic.main.fragment_account.user_img_photo

class AccountFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_account
    override val titleToolbar = R.string.screen_account

    private lateinit var mediaViewModel: MediaViewModel
    private lateinit var accountViewModel: AccountViewModel

    private var user: User? = null

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

        accountViewModel.userData.observe(viewLifecycleOwner, Observer(::handleUser))
        accountViewModel.editUserData.observe(
            viewLifecycleOwner,
            Observer(::handleEditingAccount)
        )
        mediaViewModel.cameraFileCreatedData.observe(
            viewLifecycleOwner,
            Observer(::onCameraFileCreated)
        )
        mediaViewModel.pickedImageData.observe(viewLifecycleOwner, Observer(::onImagePicked))
        mediaViewModel.progressData.observe(viewLifecycleOwner, Observer(::updateProgress))
        accountViewModel.failureData.observe(viewLifecycleOwner, Observer(::handleFailure))
        mediaViewModel.failureData.observe(viewLifecycleOwner, Observer(::handleFailure))

        showProgress()
        accountViewModel.getUser()
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

            user?.run {
                name = account_input_name.text.toString()
                email = account_input_email.text.toString()
                status = account_input_status.text.toString()

                val newPassword = account_input_new_password.text.toString()
                if (newPassword.isNotEmpty()) {
                    password = newPassword
                }

                accountViewModel.editUser(this)
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

    override fun updateProgress(status: Boolean) {
        account_group_progress.toggleVisibility(status)
    }

    private fun validatePasswords(): Boolean {
        val currentPassword = account_input_current_password.text.toString()
        val newPassword = account_input_new_password.text.toString()

        return when {
            currentPassword.isNotEmpty() && newPassword.isNotEmpty() -> {
                if (currentPassword == user?.password) {
                    true
                } else {
                    showToast(getString(R.string.error_wrong_password))
                    false
                }
            }
            currentPassword.isEmpty() && newPassword.isEmpty() -> true
            else -> {
                showToast(getString(R.string.error_empty_password))
                false
            }
        }
    }

    private fun validateFields(): Boolean {
        requireActivity().hideKeyboard()
        val allFields = arrayOf(account_input_email, account_input_name)
        var allValid = true
        for (field in allFields) {
            allValid = field.testValidity() && allValid
        }
        return allValid
    }

    private fun handleUser(user: User?) {
        hideProgress()
        this.user = user
        user?.let {
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
            user?.image = pickedImage.string

            val placeholder = user_img_photo.drawable
            Glide.with(this)
                .load(pickedImage.bitmap)
                .placeholder(placeholder)
                .error(placeholder)
                .into(user_img_photo)
        }
    }

    private fun handleEditingAccount(account: User?) {
        showToast(getString(R.string.success_edit_user))
        accountViewModel.getUser()
    }
}