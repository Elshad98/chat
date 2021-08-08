package com.example.chat.remote.core

import android.content.Context
import com.example.chat.extensions.networkInfo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Injectable class which returns information about the network connection state.
 */
@Singleton
class NetworkHandler @Inject constructor(
    private val context: Context
) {

    val isConnected
        get() = context.networkInfo?.isConnected
}
