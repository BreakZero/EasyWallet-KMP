package com.easy.wallet.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.easy.wallet.design.component.ThemePreviews
import com.easy.wallet.home.HomeEvent

@Composable
internal fun GuestContent(
    modifier: Modifier = Modifier,
    onEvent: (HomeEvent) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.weight(1.0f))
        Column(modifier = Modifier.fillMaxWidth()) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onEvent(HomeEvent.CreateWalletEvent) }) {
                Text(text = "Create wallet")
            }
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onEvent(HomeEvent.ImportWalletEvent) }) {
                Text(text = "I already have one")
            }
        }
    }
}

@ThemePreviews
@Composable
private fun GuestContent_Preview() {
    GuestContent(onEvent = {})
}