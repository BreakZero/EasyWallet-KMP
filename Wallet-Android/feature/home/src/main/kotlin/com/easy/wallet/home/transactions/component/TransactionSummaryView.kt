package com.easy.wallet.home.transactions.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EasyWalletTheme
import com.easy.wallet.shared.model.transaction.Direction
import com.easy.wallet.shared.model.transaction.EthereumTransactionUiModel
import com.easy.wallet.shared.model.transaction.TransactionStatus
import com.easy.wallet.shared.model.transaction.TransactionUiModel

@Composable
internal fun TransactionSummaryView(
    modifier: Modifier = Modifier,
    transaction: TransactionUiModel,
    itemClicked: (TransactionUiModel) -> Unit
) {
    Card(modifier = modifier, onClick = { itemClicked(transaction) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(text = transaction.datetime)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val accentColor = when (transaction.status) {
                    TransactionStatus.Confirmed -> MaterialTheme.colorScheme.primaryContainer
                    TransactionStatus.Pending -> MaterialTheme.colorScheme.secondary
                    TransactionStatus.Failed -> MaterialTheme.colorScheme.error
                }
                Icon(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(8.dp),
                    imageVector = transaction.icon(), contentDescription = null
                )
                Column(modifier = Modifier) {
                    Text(text = transaction.actionTitle())
                    Text(color = accentColor, text = transaction.status.name)
                }
                Spacer(modifier = Modifier.weight(1.0f))
                Text(text = "${transaction.amount} ${transaction.symbol}")
            }
        }
    }
}

private fun TransactionUiModel.actionTitle(): String {
    val isSend = this.direction == Direction.SEND
    val methodName = (this as? EthereumTransactionUiModel)?.functionName?.split("(")?.firstOrNull()
    return if (methodName.isNullOrBlank()) {
        if (isSend) "Send $symbol" else "Received $symbol"
    } else {
        methodName
    }
}

private fun TransactionUiModel.icon(): ImageVector {
    val isSend = this.direction == Direction.SEND
    return if (isSend) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward
}

@ThemePreviews
@Composable
private fun TransactionView_Preview() {
    EasyWalletTheme {
        Surface {
            TransactionSummaryView(
                transaction = EthereumTransactionUiModel(
                    hash = "0x07e8f83aaa03cb24b0cda6931cfd4a4fe9ac329524b53068c34e5dfdf111f0d0",
                    amount = "2.123",
                    recipient = "0x81080a7e991bcdddba8c2302a70f45d6bd369ab5",
                    sender = "0x95703ea3622e3cc5bd295ea9904414a90e3e51e7",
                    direction = Direction.SEND,
                    gas = "21000",
                    gasUsed = "62000000000",
                    gasPrice = "21000",
                    symbol = "ETH",
                    functionName = null,
                    datetime = "Mar 4 at 10:04 AM",
                    status = TransactionStatus.Confirmed
                ),
                itemClicked = {}
            )
        }
    }
}
