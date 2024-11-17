package com.easy.wallet.android.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<E> : ViewModel() {
  private val _navigationEvents = Channel<E>()
  val navigationEvents = _navigationEvents.receiveAsFlow()

  abstract fun handleEvent(event: E)

  /**
   * @Option1 If a event only for reduce a UiState, we don't need to dispatch
   * @Option2 If a event will trigger a navigation change need to dispatch
   */
  protected fun dispatchEvent(event: E) {
    viewModelScope.launch { _navigationEvents.send(event) }
  }
}
