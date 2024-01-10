package com.easy.wallet.onboard.create.secure

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.easy.wallet.android.core.extensions.ObserveAsEvents
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EasyWalletTheme
import com.easy.wallet.onboard.R
import com.easy.wallet.onboard.create.CreateWalletEvent
import com.easy.wallet.onboard.create.CreateWalletViewModel
import com.easy.wallet.onboard.create.component.TopBar

@Composable
internal fun SecureRoute(
    viewModel: CreateWalletViewModel,
    nextToCheckSeed: () -> Unit
) {
    ObserveAsEvents(flow = viewModel.navigationEvents) {
        when (it) {
            is CreateWalletEvent.NextToCheckSeed -> nextToCheckSeed()
            else -> Unit
        }
    }
    SecureScreen(onEvent = viewModel::handleEvent)
}

@Composable
internal fun SecureScreen(
    onEvent: (CreateWalletEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        TopBar(
            step = 2,
            totalStep = 3,
            navigationIcon = {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            },
            navigationAction = {
                // close and remove saved password if it exists
            },
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.create_wallet_secure_your_wallet),
            style = MaterialTheme.typography.headlineLarge,
        )
        Spacer(modifier = Modifier.height(48.dp))
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 265.0F / 191.0F),
            painter = painterResource(id = R.drawable.ic_secure),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(48.dp))
        Text(text = stringResource(id = R.string.create_wallet_secure_desc))
        Spacer(modifier = Modifier.weight(1.0f))
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { /*TODO*/ },
        ) {
            Text(text = "Remind Me Later")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onEvent(CreateWalletEvent.NextToCheckSeed) },
        ) {
            Text(text = "Start")
        }
    }
}

@Composable
@ThemePreviews
private fun SecurePreview() {
    EasyWalletTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SecureScreen {}
        }
    }
}
