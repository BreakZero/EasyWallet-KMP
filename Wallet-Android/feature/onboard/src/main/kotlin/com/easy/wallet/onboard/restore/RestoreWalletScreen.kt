package com.easy.wallet.onboard.restore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easy.wallet.android.core.extensions.ObserveAsEvents
import com.easy.wallet.onboard.R
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun RestoreWalletRoute(
    onImportSuccess: () -> Unit
) {
    val viewModel = koinViewModel<RestoreWalletViewModel>()
    ObserveAsEvents(flow = viewModel.navigationEvents) {
        onImportSuccess()
    }
    val uiState by viewModel.restoreWalletUiState.collectAsStateWithLifecycle()
    val seedPhraseForm = viewModel.seedPhraseForm
    RestoreWalletScreen(seedPhraseForm, uiState, viewModel::handleEvent)
}

@Composable
internal fun RestoreWalletScreen(
    seedPhraseForm: SeedPhraseForm,
    uiState: RestoreWalletUiState,
    onEvent: (RestoreWalletEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = stringResource(R.string.restore_wallet_import_from_seed),
                style = MaterialTheme.typography.headlineLarge,
            )

            TextField(
                label = { Text(text = stringResource(R.string.restore_wallet_seed_phrase)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                value = seedPhraseForm.seedPhrase,
                isError = !uiState.seedPhraseError.isNullOrBlank(),
                onValueChange = {
                    onEvent(RestoreWalletEvent.SeedChanged(it))
                },
            )
            TextField(
                value = seedPhraseForm.password,
                label = {
                    Text(text = stringResource(R.string.restore_wallet_new_password))
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                isError = !uiState.passwordError.isNullOrBlank(),
                onValueChange = {
                    onEvent(RestoreWalletEvent.PasswordChanged(it))
                },
                trailingIcon = {
                    Icon(imageVector = Icons.Default.RemoveRedEye, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth(),
            )
            TextField(
                value = seedPhraseForm.confirmPassword,
                label = {
                    Text(text = stringResource(R.string.restore_wallet_confirm_password))
                },
                isError = !uiState.confirmPasswordError.isNullOrBlank(),
                onValueChange = {
                    onEvent(RestoreWalletEvent.ConfirmPasswordChanged(it))
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                    )
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onEvent(RestoreWalletEvent.OnImport)
            },
        ) {
            Text(text = stringResource(R.string.restore_wallet_import))
        }
    }
}
