package com.easy.wallet.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.UiText
import com.easy.wallet.home.R

internal enum class ActionSheetMenu(
    val title: UiText,
    val desc: UiText,
    val trailingIcon: ImageVector
) {
    CREATE_BY_SEED(
        title = UiText.StringResource(resId = R.string.onboard_seed_phrase_title),
        desc = UiText.StringResource(resId = R.string.onboard_seed_phrase_create_desc),
        trailingIcon = Icons.Default.Wallet,
    ),
    RESTORE_BY_SEED(
        title = UiText.StringResource(resId = R.string.onboard_seed_phrase_title),
        desc = UiText.StringResource(resId = R.string.onboard_seed_phrase_import_desc),
        trailingIcon = Icons.Default.Wallet,
    )
}

@Composable
internal fun ActionSheetItem(
    modifier: Modifier = Modifier,
    menu: ActionSheetMenu,
    onClick: () -> Unit
) {
    Card(
        shape = CardDefaults.shape,
        modifier = modifier
            .padding(horizontal = 16.dp)
            .clip(CardDefaults.shape)
            .clickable {
                onClick()
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(horizontal = 12.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1.0f),
            ) {
                Text(text = menu.title.asString(), style = MaterialTheme.typography.headlineMedium)
                Text(text = menu.desc.asString(), style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Image(
                modifier = Modifier.size(48.dp),
                imageVector = menu.trailingIcon,
                contentDescription = menu.title.asString(),
            )
        }
    }
}