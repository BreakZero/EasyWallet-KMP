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
import androidx.compose.material3.Card
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
import com.easy.wallet.design.ui.EasyWalletTheme
import com.easy.wallet.shared.model.transaction.Direction
import com.easy.wallet.shared.model.transaction.EthereumTransactionUiModel
import com.easy.wallet.shared.model.transaction.TransactionUiModel

private fun String.ellipsize(len: Int): String {
    if (this.length > 2 * len) {
        return "${this.take(len)}...${this.takeLast(len)}"
    }
    return this
}

@Composable
internal fun TransactionSummaryView(
    modifier: Modifier = Modifier,
    transaction: TransactionUiModel,
    itemClicked: (TransactionUiModel) -> Unit
) {
    val isSend = transaction.direction == Direction.SEND
    Card(onClick = { itemClicked(transaction) }) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val label = if (isSend) "Send" else "Receive"
            val isEthContractCall =
                transaction is EthereumTransactionUiModel && transaction.functionName?.isNotBlank() == true
            val desc = when {
                isEthContractCall -> {
                    val funcName =
                        (transaction as EthereumTransactionUiModel).functionName?.split("(")
                            ?.firstOrNull().orEmpty()
                    funcName
                }

                isSend -> "To ${transaction.recipient.ellipsize(5)}"
                else -> "From ${transaction.sender.ellipsize(4)}"
            }
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
            if (isEthContractCall) {
                Text(text = desc, style = MaterialTheme.typography.titleMedium)
            } else {
                Column {
                    Text(text = label, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = desc,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1.0f))
            Text(text = "${transaction.amount} ${transaction.symbol}")
        }
    }
}

@ThemePreviews
@Composable
private fun TransactionView_Preview() {
    EasyWalletTheme {
        Surface {
            TransactionSummaryView(
                transaction = EthereumTransactionUiModel(
                    hash = "0x07e8f83aaa03cb24b0cda6931cfd4a4fe9ac329524b53068c34e5dfdf111f0d0",
                    amount = "20000000000000000",
                    recipient = "0x81080a7e991bcdddba8c2302a70f45d6bd369ab5",
                    sender = "0x95703ea3622e3cc5bd295ea9904414a90e3e51e7",
                    direction = Direction.SEND,
                    gas = "21000",
                    gasUsed = "62000000000",
                    gasPrice = "21000",
                    symbol = "ETH",
                    functionName = ""
                ),
                itemClicked = {}
            )
        }
    }
}
