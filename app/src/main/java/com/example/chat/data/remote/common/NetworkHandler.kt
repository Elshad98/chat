package com.example.chat.data.remote.common

import android.content.Context
import android.net.NetworkCapabilities
import android.os.Build
import com.example.chat.core.extension.getConnectivityManager
import toothpick.InjectConstructor

@InjectConstructor
class NetworkHandler(
    private val context: Context
) {

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getConnectivityManager()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager
                .getNetworkCapabilities(connectivityManager.activeNetwork)
                ?.run {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_VPN) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)
                }
                ?: false
        } else {
            connectivityManager
                .activeNetworkInfo
                ?.isConnected
                ?: false
        }
    }
}