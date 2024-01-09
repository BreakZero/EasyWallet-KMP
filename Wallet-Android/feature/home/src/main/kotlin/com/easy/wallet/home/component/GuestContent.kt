package com.easy.wallet.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EWalletTheme
import com.easy.wallet.home.HomeEvent
import com.easy.wallet.home.HomeUiState
import com.easy.wallet.home.R

@Composable
internal fun GuestContent(
    modifier: Modifier = Modifier,
    guestUiState: HomeUiState.GuestUiState,
    onEvent: (HomeEvent) -> Unit
) {
    var isShowActions by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier.wrapContentSize(),
                painter = painterResource(id = R.drawable.ic_setup_wallet),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.headlineMedium,
                text = "Wallet Setup",
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyLarge,
                text = "Import an existing wallet \n or create a new one",
            )
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                isShowActions = true
                onEvent(HomeEvent.OpenCreateWalletActions)
            },
        ) {
            Text(text = stringResource(id = R.string.onboard_create_wallet))
        }
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                isShowActions = true
                onEvent(HomeEvent.OpenRestoreWalletActions)
            },
        ) {
            Text(text = stringResource(id = R.string.onboard_import_wallet))
        }
    }
    if (isShowActions) {
        WalletActionSheet(
            modifier = Modifier.fillMaxHeight(0.5f),
            menus = guestUiState.actions,
            onDismiss = { isShowActions = false },
            onEvent = onEvent,
        )
    }
}

@ThemePreviews
@Composable
private fun GuestContent_Preview() {
    EWalletTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            GuestContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                guestUiState = HomeUiState.GuestUiState(),
                onEvent = {},
            )
        }
    }
}
