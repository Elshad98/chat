package com.example.chat.core.extension

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

fun Context.showToast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Context.showToast(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_LONG).show()
}

fun Context.getFontCompat(@FontRes id: Int): Typeface? =
    ResourcesCompat.getFont(this, id)

fun Context.getDrawableCompat(@DrawableRes id: Int): Drawable? =
    ContextCompat.getDrawable(this, id)

fun Context.getConnectivityManager() =
    getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun Context.getInputMethodManager() =
    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

fun Context.getNotificationManager() =
    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

fun Context.isPermissionGranted(permission: String): Boolean =
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun Context.arePermissionsGranted(vararg permissions: String): Boolean =
    permissions.all(::isPermissionGranted)

fun Context.openSystemSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", packageName, null)
    }
    startActivity(intent)
}