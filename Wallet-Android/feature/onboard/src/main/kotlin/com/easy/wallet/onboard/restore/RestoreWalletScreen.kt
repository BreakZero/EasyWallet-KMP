package com.easy.wallet.onboard.restore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.easy.wallet.android.core.extensions.ObserveAsEvents
import com.easy.wallet.onboard.R
import com.easy.wallet.onboard.components.MnemonicInputView
import com.easy.wallet.onboard.components.PasswordTextField
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun RestoreWalletRoute(
    onImportSuccess: () -> Unit
) {
    val viewModel = koinViewModel<RestoreWalletViewModel>()
    ObserveAsEvents(flow = viewModel.navigationEvents) {
        onImportSuccess()
    }

    LaunchedEffect(key1 = viewModel) {
        viewModel.checkConfirmPassword()
    }

    RestoreWalletScreen(
        seedPhraseFieldState = viewModel.seedPhraseFieldState,
        passwordFieldState = viewModel.passwordFieldState,
        confirmPasswordFieldState = viewModel.confirmPasswordFieldState,
        isPasswordValid = viewModel.isPasswordValid,
        onEvent = viewModel::handleEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RestoreWalletScreen(
    seedPhraseFieldState: TextFieldState,
    passwordFieldState: TextFieldState,
    confirmPasswordFieldState: TextFieldState,
    isPasswordValid: Boolean,
    onEvent: (RestoreWalletEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.wallet_android_feature_onboard_restore_wallet_import_from_seed)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBackIos,
                            contentDescription = null
                        )
                    }
                })
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = {
                    onEvent(RestoreWalletEvent.OnImport)
                },
            ) {
                Text(text = stringResource(R.string.wallet_android_feature_onboard_restore_wallet_import))
            }
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            MnemonicInputView(
                modifier = Modifier.fillMaxWidth(),
                textFieldState = seedPhraseFieldState,
                placeholder = { Text(text = "Enter your seed phrase") }
            )
            PasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                textField = passwordFieldState,
                placeholder = { Text(text = stringResource(R.string.wallet_android_feature_onboard_restore_wallet_new_password)) },
                isError = !isPasswordValid,
                trailingIcon = {
                    Icon(imageVector = Icons.Default.RemoveRedEye, contentDescription = null)
                }
            )
            PasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                textField = confirmPasswordFieldState,
                placeholder = { Text(text = stringResource(R.string.wallet_android_feature_onboard_restore_wallet_confirm_password)) }
            )
        }
    }
}
