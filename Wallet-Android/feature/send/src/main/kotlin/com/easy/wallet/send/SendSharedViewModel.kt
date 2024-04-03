package com.easy.wallet.send

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.easy.wallet.android.core.BaseViewModel

@OptIn(ExperimentalFoundationApi::class)
internal class SendSharedViewModel : BaseViewModel<SendUiEvent>() {

    val destination = TextFieldState("")
    var amount by mutableStateOf("")

    override fun handleEvent(event: SendUiEvent) {
        when (event) {
            SendUiEvent.BackSpace -> {
                amount = amount.dropLast(1)
            }

            SendUiEvent.ClickNext -> dispatchEvent(event)
            is SendUiEvent.EnterDigital -> {
                amount += event.char
            }

            SendUiEvent.MaxAmount -> {
                amount = "88"
            }
        }
    }
}
