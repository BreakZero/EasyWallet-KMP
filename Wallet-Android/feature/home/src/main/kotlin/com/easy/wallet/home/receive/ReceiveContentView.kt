package com.easy.wallet.home.receive

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.unit.dp
import com.easy.wallet.home.utils.rememberQrBitmapPainter
import com.easy.wallet.model.TokenInformation

@Composable
internal fun ReceiveContent(
    address: String,
    tokenInformation: TokenInformation,
    painter: BitmapPainter = rememberQrBitmapPainter(content = address)
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier,
            style = MaterialTheme.typography.headlineMedium,
            text = "Received BNB"
        )

        Image(
            modifier = Modifier.fillMaxWidth(0.8f)
                .aspectRatio(1.0f)
                .padding(12.dp),
            painter = rememberQrBitmapPainter(content = "0xDAFEA492D9c6733ae3d56b7Ed1ADB60692c98Bc5", padding = 1.dp),
            contentDescription = null
        )

        Text(text = "Scan address to Receive payment")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ElevatedButton(modifier = Modifier.weight(1.0f), onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null)
                Text(text = "bc1q87...34pm")
            }
            ElevatedButton(modifier = Modifier.weight(1.0f), onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Share, contentDescription = null)
                Text(text = "Share")
            }
        }
        ElevatedButton(modifier = Modifier.fillMaxWidth(), onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.ArrowUpward, contentDescription = null)
            Text(text = "Send Link")
        }
    }
}
