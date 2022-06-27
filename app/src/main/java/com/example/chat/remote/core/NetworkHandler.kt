package com.example.chat.remote.core

import android.content.Context
import android.net.NetworkCapabilities
import android.os.Build
import com.example.chat.extensions.getConnectivityManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Injectable class which returns information about the network connection state.
 */
@Singleton
class NetworkHandler @Inject constructor(
    private val context: Context
) {

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getConnectivityManager()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager
                .getNetworkCapabilities(connectivityManager.activeNetwork)
                ?.let { capabilities ->
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)
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