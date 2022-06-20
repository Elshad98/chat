package com.example.chat.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.longToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Context.longToast(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_LONG).show()
}

fun Context.shortToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.shortToast(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
}

fun Context.getConnectivityManager() =
    getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun Context.getInputMethodManager() =
    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

val Context.isConnected: Boolean
    get() {
        val connectivityManager = getConnectivityManager()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager
                .getNetworkCapabilities(connectivityManager.activeNetwork)
                ?.let { capabilities ->
                    when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        else -> false
                    }
                }
                ?: false
        } else {
            connectivityManager
                .activeNetworkInfo
                ?.isConnected
                ?: false
        }
    }