package com.easy.wallet.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

internal enum class ActionSheetMenu(
    val title: String,
    val desc: String,
    val trailingIcon: ImageVector
) {
    CREATE_BY_SEED(
        title = "Seed phrase",
        desc = "Create a wallet by generating seed phrase",
        trailingIcon = Icons.Default.Wallet
    ),
    IMPORT_BY_SEED(
        title = "Seed phrase",
        desc = "12,18,24-word seed phrases are supported",
        trailingIcon = Icons.Default.Wallet
    )
}

@Composable
internal fun ActionSheetItem(
    modifier: Modifier = Modifier,
    menu: ActionSheetMenu,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable {
                onClick()
            }
            .padding(horizontal = 12.dp, vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier.weight(1.0f)
        ) {
            Text(text = menu.title, style = MaterialTheme.typography.titleMedium)
            Text(text = menu.desc, style = MaterialTheme.typography.bodyMedium)
        }
        Image(imageVector = menu.trailingIcon, contentDescription = menu.title)
    }
}