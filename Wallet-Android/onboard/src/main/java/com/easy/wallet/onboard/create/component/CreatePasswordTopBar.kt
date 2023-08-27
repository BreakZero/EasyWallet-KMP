package com.easy.wallet.onboard.create.component

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun TopBar(
    step: Int,
    totalStep: Int,
    navigationIcon: @Composable () -> Unit = {},
    navigationAction: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = navigationAction) {
            navigationIcon()
        }
        LinearProgressIndicator(
            progress = step.toFloat().div(totalStep),
            modifier = Modifier
                .weight(1.0f)
                .padding(horizontal = 6.dp)
        )
        Text(text = "$step/$totalStep")
    }
}