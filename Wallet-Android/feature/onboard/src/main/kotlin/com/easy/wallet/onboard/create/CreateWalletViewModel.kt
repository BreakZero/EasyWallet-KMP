package com.easy.wallet.onboard.create

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.datastore.UserPasswordStorage
import com.easy.wallet.onboard.util.PasswordValidateResult
import com.easy.wallet.onboard.util.PasswordValidation
import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.domain.CreateWalletUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class CreateWalletViewModel(
    private val userStorage: UserPasswordStorage,
    private val multiWalletRepository: MultiWalletRepository,
    private val createWalletUseCase: CreateWalletUseCase
) : BaseViewModel<CreateWalletEvent>() {
    val passwordTextFieldState = TextFieldState()
    val confirmPasswordTextFieldState = TextFieldState()

    internal var validateResult by mutableStateOf(PasswordValidateResult.PasswordNotMatch)

    internal var mnemonicPhrases = mutableListOf<String>()

    suspend fun validatePassword() {
        combine(
            snapshotFlow { passwordTextFieldState.text }.debounce(300),
            snapshotFlow { confirmPasswordTextFieldState.text }.debounce(300)
        ) { password, confirmPassword ->
            PasswordValidation.validate(password.toString(), confirmPassword.toString())
        }.collectLatest {
            validateResult = it
        }
    }

    override fun handleEvent(event: CreateWalletEvent) {
        when (event) {
            CreateWalletEvent.OnCreatePassword -> {
                PasswordValidation.validate(
                    password = passwordTextFieldState.text.toString(),
                    confirmPassword = confirmPasswordTextFieldState.text.toString()
                ).let {
                    when (it) {
                        PasswordValidateResult.PasswordNotMatch -> TODO()
                        PasswordValidateResult.ConfirmPasswordNotMatch -> TODO()
                        PasswordValidateResult.Pass -> dispatchEvent(CreateWalletEvent.NavigateToSecure)
                    }
                }
            }

            is CreateWalletEvent.OnStartInSecure -> {
                val mnemonicPhrases = createWalletUseCase().split(" ").toMutableStateList()
                this.mnemonicPhrases = mnemonicPhrases
                dispatchEvent(CreateWalletEvent.NavigateToMnemonic)
            }

            is CreateWalletEvent.OnCreateWallet -> {
                viewModelScope.launch {
                    if (mnemonicPhrases.isNotEmpty()) {
                        multiWalletRepository.insertOne(
                            mnemonicPhrases.joinToString(separator = " "),
                            ""
                        ) {
                            viewModelScope.launch {
                                userStorage.putPassword(
                                    dispatcher = Dispatchers.IO,
                                    passwordTextFieldState.text.toString()
                                )
                            }
                            dispatchEvent(CreateWalletEvent.NavigateToWalletTab)
                        }
                    }
                }
            }

            CreateWalletEvent.PopBack -> dispatchEvent(event)

            else -> Unit
        }
    }
}
