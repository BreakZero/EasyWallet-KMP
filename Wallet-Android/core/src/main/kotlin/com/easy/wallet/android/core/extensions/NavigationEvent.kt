package com.easy.wallet.android.core.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Composable
fun <T> ObserveAsEvents(flow: Flow<T>, onEvent: (T) -> Unit) {
  val lifecycleOwner = LocalLifecycleOwner.current
  LaunchedEffect(flow, lifecycleOwner.lifecycle) {
    lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
      // using immediate to avoid losing events
      withContext(Dispatchers.Main.immediate) {
        flow.collect(onEvent)
      }
    }
  }
}
