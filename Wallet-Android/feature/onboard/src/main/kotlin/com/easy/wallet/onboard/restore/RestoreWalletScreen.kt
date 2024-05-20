package com.easy.wallet.onboard.restore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
        viewModel.mnemonicValidate()
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RestoreWalletScreen(
        seedPhraseFieldState = viewModel.seedPhraseFieldState,
        passwordFieldState = viewModel.passwordFieldState,
        confirmPasswordFieldState = viewModel.confirmPasswordFieldState,
        uiState = uiState,
        onEvent = viewModel::handleEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RestoreWalletScreen(
    seedPhraseFieldState: TextFieldState,
    passwordFieldState: TextFieldState,
    confirmPasswordFieldState: TextFieldState,
    uiState: RestoreWalletUiState,
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
        var textObfuscationMode by remember {
            mutableStateOf(TextObfuscationMode.RevealLastTyped)
        }
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
                isError = !uiState.mnemonicError.isNullOrBlank(),
                placeholder = { Text(text = "Enter your seed phrase") }
            )
            PasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                textField = passwordFieldState,
                textObfuscationMode = textObfuscationMode,
                placeholder = { Text(text = stringResource(R.string.wallet_android_feature_onboard_restore_wallet_new_password)) },
                isError = uiState.passwordError,
                trailingIcon = {
                    IconButton(onClick = {
                        textObfuscationMode =
                            if (textObfuscationMode == TextObfuscationMode.Visible) TextObfuscationMode.RevealLastTyped else TextObfuscationMode.Visible
                    }) {
                        Icon(imageVector = Icons.Default.RemoveRedEye, contentDescription = null)
                    }
                }
            )
            PasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                textField = confirmPasswordFieldState,
                isError = uiState.confirmPasswordError,
                textObfuscationMode = textObfuscationMode,
                placeholder = { Text(text = stringResource(R.string.wallet_android_feature_onboard_restore_wallet_confirm_password)) }
            )
        }
    }
}
