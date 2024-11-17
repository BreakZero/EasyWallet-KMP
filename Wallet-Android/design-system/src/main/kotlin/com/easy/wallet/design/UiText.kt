package com.easy.wallet.design

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UiText {
  data class DynamicString(
    val text: String
  ) : UiText

  class StringResource(
    val resId: Int,
    vararg val args: Any
  ) : UiText

  @Composable
  fun asString(): String = when (this) {
    is DynamicString -> text
    is StringResource -> stringResource(resId, *args)
  }

  fun asString(context: Context): String = when (this) {
    is DynamicString -> text
    is StringResource -> context.getString(resId, *args)
  }
}
