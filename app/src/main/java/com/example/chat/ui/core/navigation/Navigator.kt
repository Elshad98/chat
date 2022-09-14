package com.example.chat.ui.core.navigation

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.chat.R
import com.example.chat.domain.friends.FriendEntity
import com.example.chat.presentation.Authenticator
import com.example.chat.presentation.viewmodel.MediaViewModel
import com.example.chat.remote.service.AccountService
import com.example.chat.ui.account.AccountActivity
import com.example.chat.ui.core.PermissionManager
import com.example.chat.ui.home.HomeActivity
import com.example.chat.ui.login.ForgetPasswordActivity
import com.example.chat.ui.login.LoginActivity
import com.example.chat.ui.messages.MessagesActivity
import com.example.chat.ui.register.RegisterActivity
import com.example.chat.ui.user.UserActivity
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.android.synthetic.main.dialog_image.view.image_view

@Singleton
class Navigator @Inject constructor(
    private val authenticator: Authenticator,
    private val permissionManager: PermissionManager
) {

    fun showMain(context: Context) {
        authenticator.userLoggedIn {
            if (it) {
                showHome(context, false)
            } else {
                showLogin(context, false)
            }
        }
    }

    fun showForgetPassword(context: Context) {
        context.startActivity<ForgetPasswordActivity>()
    }

    fun showLogin(context: Context, newTask: Boolean = true) {
        context.startActivity<LoginActivity>(newTask)
    }

    fun showHome(context: Context, newTask: Boolean = true) {
        context.startActivity<HomeActivity>(newTask)
    }

    fun showSignUp(context: Context) {
        context.startActivity<RegisterActivity>()
    }

    fun showAccount(context: Context) {
        context.startActivity<AccountActivity>()
    }

    fun showUser(context: Context, friendEntity: FriendEntity) {
        val bundle = Bundle().apply {
            putString(AccountService.PARAM_NAME, friendEntity.name)
            putString(AccountService.PARAM_EMAIL, friendEntity.email)
            putString(AccountService.PARAM_IMAGE, friendEntity.image)
            putLong(AccountService.PARAM_CONTACT_ID, friendEntity.id)
            putString(AccountService.PARAM_STATUS, friendEntity.status)
        }
        context.startActivity<UserActivity>(args = bundle)
    }

    fun showCamera(activity: AppCompatActivity, uri: Uri) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)

        activity.startActivityForResult(intent, MediaViewModel.CAPTURE_IMAGE_REQUEST_CODE)
    }

    fun showGallery(activity: AppCompatActivity) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"

        activity.startActivityForResult(intent, MediaViewModel.PICK_IMAGE_REQUEST_CODE)
    }

    fun showEmailInvite(context: Context, email: String) {
        val emailIntent = Intent().apply {
            action = Intent.ACTION_SENDTO
            data = Uri.fromParts("mailto", email, null)
            putExtra(
                Intent.EXTRA_SUBJECT,
                context.getString(R.string.message_subject_prompt_app)
            )
            putExtra(
                Intent.EXTRA_TEXT,
                context.getString(R.string.message_text_prompt_app) +
                    " " + context.getString(R.string.url_google_play) + context.packageName
            )
        }
        context.startActivity(Intent.createChooser(emailIntent, context.getString(R.string.send)))
    }

    fun showPickFromDialog(activity: AppCompatActivity, onPick: (fromCamera: Boolean) -> Unit) {
        val options = arrayOf<CharSequence>(
            activity.getString(R.string.camera),
            activity.getString(R.string.gallery)
        )
        AlertDialog.Builder(activity)
            .setTitle(activity.getString(R.string.pick))
            .setItems(options) { _, item ->
                when (options[item]) {
                    activity.getString(R.string.camera) -> {
                        permissionManager.checkCameraPermission(activity) {
                            onPick(true)
                        }
                    }
                    activity.getString(R.string.gallery) -> {
                        permissionManager.checkWritePermission(activity) {
                            onPick(false)
                        }
                    }
                }
            }
            .show()
    }

    fun showEmailNotFoundDialog(context: Context, email: String) {
        AlertDialog.Builder(context)
            .setMessage(context.getString(R.string.message_prompt_app))
            .setPositiveButton(android.R.string.ok) { _, _ ->
                showEmailInvite(context, email)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    fun showChatWithContact(context: Context, contactId: Long, contactName: String) {
        val bundle = Bundle().apply {
            putString(AccountService.PARAM_NAME, contactName)
            putLong(AccountService.PARAM_CONTACT_ID, contactId)
        }
        context.startActivity<MessagesActivity>(args = bundle)
    }

    fun showDeleteMessageDialog(context: Context, onPositive: () -> Unit) {
        AlertDialog.Builder(context)
            .setMessage(context.getString(R.string.delete_message_text))
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                onPositive()
                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    fun showImageDialog(context: Context, image: Drawable) {
        val view = LayoutInflater.from(context).inflate(
            R.layout.dialog_image,
            null
        )

        val dialog = Dialog(context, R.style.DialogFullscreen)

        view.image_view.setImageDrawable(image)
        dialog.setContentView(view)

        view.image_view.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }
}

private inline fun <reified T : AppCompatActivity> Context.startActivity(
    newTask: Boolean = false,
    args: Bundle? = null
) {
    val intent = Intent(this, T::class.java).apply {
        if (newTask) {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        putExtra("args", args)
    }
    this.startActivity(intent)
}