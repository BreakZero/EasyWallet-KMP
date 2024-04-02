package com.easy.wallet.home.receive

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.unit.dp
import com.easy.wallet.home.utils.rememberQrBitmapPainter
import com.easy.wallet.model.TokenBasicResult

private fun String.ellipsize(len: Int): String {
    if (this.length > 2 * len) {
        return "${this.take(len)}...${this.takeLast(len)}"
    }
    return this
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ReceiveContentSheet(
    modifier: Modifier = Modifier,
    basicResult: TokenBasicResult,
    painter: BitmapPainter = rememberQrBitmapPainter(content = basicResult.address, padding = 1.dp),
    onDismissRequest: () -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        modifier = modifier,
        sheetState = bottomSheetState,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier,
                style = MaterialTheme.typography.headlineMedium,
                text = "Received ${basicResult.symbol}"
            )

            Image(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .aspectRatio(1.0f)
                    .padding(12.dp),
                painter = painter,
                contentDescription = null
            )

            Text(text = "Scan address to Receive payment")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ElevatedButton(modifier = Modifier.weight(1.0f), onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null)
                    Text(text = basicResult.address.ellipsize(4))
                }
                ElevatedButton(modifier = Modifier.weight(1.0f), onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = null)
                    Text(text = "Share")
                }
            }
            ElevatedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.elevatedButtonColors()
                    .copy(containerColor = MaterialTheme.colorScheme.surfaceContainer)
            ) {
                Icon(imageVector = Icons.Default.ArrowUpward, contentDescription = null)
                Text(text = "Send Link")
            }
        }
    }
}
