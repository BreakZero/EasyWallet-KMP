package com.easy.wallet.settings.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EasyWalletTheme

@Composable
internal fun SettingsItem(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    suffix: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1.0f),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
            )
            subtitle?.let {
                Text(style = MaterialTheme.typography.labelMedium, text = it)
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        if (suffix != null) suffix()
    }
}

@Composable
internal fun ExtendSettingsItem(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    SettingsItem(
        modifier = modifier,
        title = title,
        subtitle = subtitle,
        suffix = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null
            )
        },
        onClick = onClick
    )
}

@ThemePreviews
@Composable
private fun SettingsItem_Preview() {
    EasyWalletTheme {
        Surface {
            Column {
                SettingsItem(
                    title = "General",
                    subtitle = "Currency conversion, primary currency, language and so on",
                ) {}
                ExtendSettingsItem(title = "Title") {

                }
                SettingsItem(
                    title = "Enable Biometric",
                    suffix = {
                        Switch(checked = false, onCheckedChange = {})
                    }
                )
            }
        }
    }
}
