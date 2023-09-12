package com.easy.wallet.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.component.ThemePreviews
import com.easy.wallet.design.ui.EWalletTheme
import com.easy.wallet.settings.component.SettingsItem
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SettingsRoute(
    popBack: () -> Unit
) {
    val viewModel: SettingsViewModel = koinViewModel()
    LaunchedEffect(key1 = viewModel) {
        viewModel.eventFlow.collect {
            when (it) {
                SettingsEvent.PopBack -> popBack()
            }
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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 12.dp)
                .padding(top = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SettingsItem(
                title = "General",
                subtitle = "Currency conversion, primary currency, language and so on"
            )
            SettingsItem(
                title = "General",
                subtitle = "Currency conversion, primary currency, language and so on"
            )
            SettingsItem(
                title = "General",
                subtitle = "Currency conversion, primary currency, language and so on"
            )
            SettingsItem(
                title = "General",
                subtitle = "Currency conversion, primary currency, language and so on"
            )
            SettingsItem(
                title = "General",
                subtitle = "Currency conversion, primary currency, language and so on"
            )
        }
    }
}

@ThemePreviews
@Composable
private fun Settings_Preview() {
    EWalletTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SettingsScreen {}
        }
    }
}