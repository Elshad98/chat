package com.example.chat.remote.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Injectable class which returns information about the network connection state.
 */
@Singleton
class NetworkHandler @Inject constructor(private val context: Context) {

    val isConnected
        get() = context.networkInfo?.isConnected
}

val Context.networkInfo: NetworkInfo?
    get() = (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
