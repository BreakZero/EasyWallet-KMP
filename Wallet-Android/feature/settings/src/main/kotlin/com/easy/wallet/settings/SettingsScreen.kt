package com.easy.wallet.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easy.wallet.android.core.extensions.ObserveAsEvents
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EasyWalletTheme
import com.easy.wallet.settings.component.ExtendSettingsItem
import com.easy.wallet.settings.component.SettingsItem
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SettingsRoute(
    navigateToSupportedChains: () -> Unit,
    navigateTokenManager: () -> Unit,
    popBack: () -> Unit
) {
    val viewModel: SettingsViewModel = koinViewModel()
    ObserveAsEvents(flow = viewModel.navigationEvents) {
        when (it) {
            SettingsEvent.PopBack -> popBack()
            SettingsEvent.ClickViewSupportedChains -> navigateToSupportedChains()
            SettingsEvent.ClickTokenManager -> navigateTokenManager()
            else -> Unit
        }
    }
    val uiState by viewModel.settingsUiState.collectAsStateWithLifecycle()
    SettingsScreen(onEvent = viewModel::handleEvent, settingsUiState = uiState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsScreen(
    settingsUiState: SettingsUiState,
    onEvent: (SettingsEvent) -> Unit
) {
    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Settings") },
                navigationIcon = {
                    IconButton(onClick = { onEvent(SettingsEvent.PopBack) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
            )
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 12.dp)
                .padding(top = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            val modifier = Modifier.fillMaxWidth()
            ExtendSettingsItem(
                modifier = modifier,
                title = "General",
                subtitle = "Currency conversion, primary currency, language and so on",
                onClick = {}
            )
            HorizontalDivider()
            ExtendSettingsItem(
                modifier = modifier,
                title = "View Supported Chains",
                onClick = {
                    onEvent(SettingsEvent.ClickViewSupportedChains)
                }
            )
            ExtendSettingsItem(
                modifier = modifier,
                title = "Token Manager",
                onClick = {
                    onEvent(SettingsEvent.ClickTokenManager)
                }
            )
            HorizontalDivider()
            SettingsItem(
                modifier = modifier,
                title = "Enable Biometric",
                suffix = {
                    Switch(
                        checked = settingsUiState.biometricEnabled,
                        onCheckedChange = {
                            onEvent(SettingsEvent.BiometricCheckedChanged(it))
                        }
                    )
                }
            )
        }
    }
}

@ThemePreviews
@Composable
private fun Settings_Preview() {
    EasyWalletTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SettingsScreen(settingsUiState = SettingsUiState(biometricEnabled = false)) {}
        }
    }
}
