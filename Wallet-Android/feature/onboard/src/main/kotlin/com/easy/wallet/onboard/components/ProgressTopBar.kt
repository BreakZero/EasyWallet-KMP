package com.easy.wallet.onboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProgressTopBar(
    modifier: Modifier = Modifier,
    step: Int,
    totalSteps: Int,
    navigationIcon: @Composable () -> Unit = {},
    action: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IconButton(onClick = action) {
            navigationIcon()
        }
        LinearProgressIndicator(
            progress = { step.toFloat().div(totalSteps) },
            modifier = Modifier.weight(1F)
        )
        Text(text = "$step/$totalSteps")
    }
}
