package com.easy.wallet.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.easy.wallet.android.core.extensions.ObserveAsEvents
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EasyWalletTheme
import com.easy.wallet.settings.component.ExtendSettingsItem
import com.easy.wallet.settings.component.SettingsItem
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SettingsRoute(
    navigateChainManager: () -> Unit,
    navigateTokenManager: () -> Unit,
    popBack: () -> Unit
) {
    val viewModel: SettingsViewModel = koinViewModel()
    ObserveAsEvents(flow = viewModel.navigationEvents) {
        when (it) {
            SettingsEvent.PopBack -> popBack()
            SettingsEvent.ClickChainManager -> navigateChainManager()
            SettingsEvent.ClickTokenManager -> navigateTokenManager()
        }
    }
    SettingsScreen(onEvent = viewModel::handleEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsScreen(
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
                        Icon(imageVector = Icons.Default.ArrowBackIos, contentDescription = "")
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
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ExtendSettingsItem(
                title = "General",
                subtitle = "Currency conversion, primary currency, language and so on",
                onClick = {}
            )
            HorizontalDivider()
            ExtendSettingsItem(
                title = "Chain Manager",
                onClick = {
                    onEvent(SettingsEvent.ClickChainManager)
                }
            )
            ExtendSettingsItem(
                title = "Token Manager",
                onClick = {
                    onEvent(SettingsEvent.ClickTokenManager)
                }
            )
            HorizontalDivider()
            SettingsItem(
                title = "Enable Biometric",
                suffix = {
                    Switch(checked = false, onCheckedChange = {})
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
            SettingsScreen {}
        }
    }
}
