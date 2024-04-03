package com.easy.wallet.send

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.foundation.text2.input.delete
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.send.navigation.TokenArgs
import com.easy.wallet.shared.domain.TokenAmountUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalFoundationApi::class)
internal class SendSharedViewModel(
    savedStateHandle: SavedStateHandle,
    tokenAmountUseCase: TokenAmountUseCase
) : BaseViewModel<SendUiEvent>() {

    private val tokenArgs: TokenArgs = TokenArgs(savedStateHandle)
    private val tokenId = tokenArgs.tokenId

    init {
        tokenAmountUseCase(tokenId).onEach {
            println("===== $it")
        }.launchIn(viewModelScope)
    }

    val destination = TextFieldState("")
    var amount = TextFieldState("0")

    override fun handleEvent(event: SendUiEvent) {
        when (event) {
            SendUiEvent.Backspace -> {
                amount.edit {
                    delete(length - 1, length)
                    if (length == 0) {
                        append("0")
                    }
                }
            }

            SendUiEvent.ClickNext -> dispatchEvent(event)
            is SendUiEvent.EnterDigital -> {
                amount.edit {
                    when {
                        event.char == "." && amount.text.contains(".") -> return@edit
                        event.char != "." && amount.text.contentEquals("0") -> replace(0,1, event.char)
                        else -> append(event.char)
                    }
                }
            }

            SendUiEvent.MaxAmount -> {
                amount.clearText()
                amount.edit {
                    append("88")
                }
            }
        }
    }
}
