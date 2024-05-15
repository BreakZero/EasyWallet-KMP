package com.easy.wallet.onboard.create.seed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.easy.wallet.android.core.extensions.ObserveAsEvents
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EasyWalletTheme
import com.easy.wallet.onboard.R
import com.easy.wallet.onboard.create.CreateWalletEvent
import com.easy.wallet.onboard.create.CreateWalletViewModel
import com.easy.wallet.onboard.create.component.TopBar

@Composable
internal fun SeedPhraseRoute(
    viewModel: CreateWalletViewModel,
    onCreateSuccess: () -> Unit
) {
    ObserveAsEvents(flow = viewModel.navigationEvents) {
        when (it) {
            is CreateWalletEvent.OnCreateWallet -> onCreateSuccess()
            else -> Unit
        }
    }
    SeedPhraseScreen(
        seedPhrase = viewModel.seedPhrase,
        onEvent = viewModel::handleEvent,
    )
}

@Composable
internal fun SeedPhraseScreen(
    seedPhrase: List<String>,
    onEvent: (CreateWalletEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
    ) {
        var hideSeedWords by remember {
            mutableStateOf(true)
        }
        TopBar(step = 3, totalStep = 3, navigationIcon = {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
        }) {
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.wallet_android_feature_onboard_create_wallet_secure_write_down_seed),
            style = MaterialTheme.typography.headlineLarge,
        )
        Spacer(modifier = Modifier.height(48.dp))
        Text(text = stringResource(id = R.string.wallet_android_feature_onboard_create_wallet_secure_write_down_seed_desc))
        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center,
        ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .let {
                        if (hideSeedWords) return@let it.blur(6.dp)
                        it
                    },
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(12.dp),
            ) {
                items(seedPhrase.size, key = { it }) { index ->
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(MaterialTheme.colorScheme.background)
                            .padding(vertical = 8.dp),
                        text = seedPhrase[index],
                        textAlign = TextAlign.Center,
                    )
                }
            }
            if (hideSeedWords) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            hideSeedWords = false
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(imageVector = Icons.Default.RemoveRedEye, contentDescription = null)
                    Text(text = "Tap to reveal your seed phrase")
                }
            }
        }
        Spacer(modifier = Modifier.weight(1.0f))
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = !hideSeedWords,
            onClick = {
                onEvent(CreateWalletEvent.OnCreateWallet)
            },
        ) {
            Text(text = "Continue")
        }
    }
}

@ThemePreviews
@Composable
private fun SeedScreen_Preview() {
    EasyWalletTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SeedPhraseScreen(
                (1..12).map {
                    "word-$it"
                },
                onEvent = {},
            )
        }
    }
}
