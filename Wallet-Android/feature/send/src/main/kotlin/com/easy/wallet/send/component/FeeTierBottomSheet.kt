package com.easy.wallet.send.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.easy.wallet.shared.model.fees.EthereumFee
import com.easy.wallet.shared.model.fees.FeeModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FeeTierBottomSheet(
  modifier: Modifier = Modifier,
  fees: List<FeeModel>,
  onItemClick: (FeeModel) -> Unit,
  dismiss: () -> Unit
) {
  val bottomSheetState = rememberModalBottomSheetState(
    skipPartiallyExpanded = true
  )
  ModalBottomSheet(
    modifier = modifier,
    sheetState = bottomSheetState,
    onDismissRequest = dismiss
  ) {
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .padding(bottom = 24.dp)
    ) {
      items(fees, key = { it.feeLevel }) { fee ->
        FeeTierItem(data = fee, onClick = {
          onItemClick(fee)
          dismiss()
        })
      }
    }
  }
}

@Composable
internal fun FeeTierItem(
  modifier: Modifier = Modifier,
  data: FeeModel,
  onClick: () -> Unit
) {
  if (data is EthereumFee) {
    ListItem(
      modifier = modifier.clickable(onClick = onClick),
      headlineContent = {
        Text(
          style = MaterialTheme.typography.titleMedium,
          text = data.feeLevel.name
        )
      },
      supportingContent = {
        Text(
          style = MaterialTheme.typography.labelSmall,
          text = "gas(${data.gas}) * perGas(${data.maxFeePerGas})"
        )
      },
      trailingContent = {
        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
      }
    )
  } else {
    ListItem(
      modifier = modifier,
      headlineContent = { Text(text = data.feeLevel.name) }
    )
  }
}
