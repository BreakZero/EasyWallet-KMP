package com.easy.wallet.onboard.create.password

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EWalletTheme
import com.easy.wallet.onboard.R
import com.easy.wallet.onboard.create.CreateWalletEvent
import com.easy.wallet.onboard.create.CreateWalletViewModel
import com.easy.wallet.onboard.create.PasswordUiState
import com.easy.wallet.onboard.create.component.TopBar

@Composable
internal fun CreatePasswordRoute(
    viewModel: CreateWalletViewModel,
    nextToSecure: () -> Unit,
    onClose: () -> Unit
) {
    val uiState by viewModel.passwordUiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = viewModel) {
        viewModel.eventFlow.collect {
            when (it) {
                is CreateWalletEvent.NextToSecure -> nextToSecure()
                is CreateWalletEvent.Close -> onClose()
                else -> Unit
            }
        }
    }
    CreatePasswordScreen(uiState, viewModel::handleEvent)
}

@Composable
internal fun CreatePasswordScreen(
    uiState: PasswordUiState,
    onEvent: (CreateWalletEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        TopBar(
            step = 1,
            totalStep = 3,
            navigationIcon = {
                Icon(imageVector = Icons.Default.Close, contentDescription = "")
            },
            navigationAction = {
                // close and remove saved password if it exists
                onEvent(CreateWalletEvent.Close)
            },
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.create_wallet_create_password),
            style = MaterialTheme.typography.headlineLarge,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            text = stringResource(id = R.string.create_wallet_create_password_desc),
        )
        TextField(
            value = uiState.password,
            label = {
                Text(text = "New password")
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            isError = uiState.passwordError != null,
            onValueChange = {
                onEvent(CreateWalletEvent.OnPasswordChanged(it))
            },
            trailingIcon = {
                Icon(imageVector = Icons.Default.RemoveRedEye, contentDescription = null)
            },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = uiState.confirmPassword,
            label = {
                Text(text = "Confirm password")
            },
            isError = uiState.confirmPasswordError != null,
            onValueChange = {
                onEvent(CreateWalletEvent.OnConfirmPasswordChanged(it))
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            trailingIcon = {
                if (uiState.isMatch()) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = uiState.isTermsOfServiceAgreed,
                onCheckedChange = {
                    onEvent(CreateWalletEvent.OnCheckedTermOfServiceChanged(it))
                },
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = stringResource(R.string.create_wallet_create_password_terms))
        }
        Spacer(modifier = Modifier.weight(1.0f))
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.isAvailable(),
            onClick = { onEvent(CreateWalletEvent.NextToSecure) },
        ) {
            Text(text = stringResource(id = R.string.create_wallet_create_password))
        }
    }
}

@Composable
@ThemePreviews
private fun CreatePassword_Preview() {
    EWalletTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            CreatePasswordScreen(
                uiState = PasswordUiState(),
                onEvent = {},
            )
        }
    }
}
