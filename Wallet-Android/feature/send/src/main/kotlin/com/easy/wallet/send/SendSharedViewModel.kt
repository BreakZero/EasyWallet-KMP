package com.easy.wallet.send

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
