package com.easy.wallet.home.receive

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
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
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

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
                ElevatedButton(modifier = Modifier.weight(1.0f), onClick = {
                    clipboardManager.setText(AnnotatedString(basicResult.address))
                }) {
                    Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null)
                    Text(text = basicResult.address.ellipsize(4))
                }
                ElevatedButton(modifier = Modifier.weight(1.0f), onClick = {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, basicResult.address)
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    ContextCompat.startActivity(context, shareIntent, null)
                }) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = null)
                    Text(text = "Share")
                }
            }
        }
    }
}
