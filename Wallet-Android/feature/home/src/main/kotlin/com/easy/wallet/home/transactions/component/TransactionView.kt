package com.easy.wallet.home.transactions.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EWalletTheme
import com.easy.wallet.model.data.Direction
import com.easy.wallet.model.data.EthereumTransaction
import com.easy.wallet.model.data.Transaction

@Composable
internal fun TransactionView(
    modifier: Modifier = Modifier,
    transaction: Transaction
) {
    val isSend = transaction.direction == Direction.SEND
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val label = if (isSend) "Send" else "Receive"
        val desc = if (isSend) "To ${transaction.recipient.take(5)}...${transaction.recipient.takeLast(4)}"
        else "To ${transaction.sender.take(5)}...${transaction.sender.takeLast(4)}"
        val icon = if (isSend) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward
        Icon(
            modifier = Modifier
                .size(56.dp)
                .padding(8.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(8.dp),
            imageVector = icon,
            contentDescription = null,
        )
        Column {
            Text(text = label, style = MaterialTheme.typography.titleMedium)
            Text(
                text = desc,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
        Spacer(modifier = Modifier.weight(1.0f))
        Text(text = transaction.amount)
    }
}

@ThemePreviews
@Composable
private fun TransactionView_Preview() {
    EWalletTheme {
        Surface {
            TransactionView(
                transaction = EthereumTransaction(
                    hash = "0x07e8f83aaa03cb24b0cda6931cfd4a4fe9ac329524b53068c34e5dfdf111f0d0",
                    amount = "20000000000000000",
                    recipient = "0x81080a7e991bcdddba8c2302a70f45d6bd369ab5",
                    sender = "0x95703ea3622e3cc5bd295ea9904414a90e3e51e7",
                    direction = Direction.SEND,
                    gas = "21000",
                    gasUsed = "62000000000",
                    gasPrice = "21000",
                ),
            )
        }
    }
}
