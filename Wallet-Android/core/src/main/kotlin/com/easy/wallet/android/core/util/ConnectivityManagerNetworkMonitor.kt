package com.easy.wallet.android.core.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.getSystemService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ConnectivityManagerNetworkMonitor(
  private val context: Context
) : NetworkMonitor {
  override val isOnline: Flow<Boolean> = callbackFlow {
    val connectivityManager = context.getSystemService<ConnectivityManager>()
    if (connectivityManager == null) {
      channel.trySend(false)
      channel.close()
      return@callbackFlow
    }
    val callback = object : ConnectivityManager.NetworkCallback() {
      private val networks = mutableSetOf<Network>()

      override fun onAvailable(network: Network) {
        networks += network
        channel.trySend(true)
      }

      override fun onLost(network: Network) {
        networks -= network
        channel.trySend(networks.isNotEmpty())
      }
    }
    val request = NetworkRequest
      .Builder()
      .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
      .build()
    connectivityManager.registerNetworkCallback(request, callback)

    /**
     * Sends the latest connectivity status to the underlying channel.
     */
    channel.trySend(connectivityManager.isCurrentlyConnected())

    awaitClose {
      connectivityManager.unregisterNetworkCallback(callback)
    }
  }

  private fun ConnectivityManager.isCurrentlyConnected() = activeNetwork
    ?.let(::getNetworkCapabilities)
    ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
}
