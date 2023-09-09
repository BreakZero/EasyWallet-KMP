package com.easy.wallet.home.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.easy.wallet.model.token.Token

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TokenItemView(
    modifier: Modifier = Modifier,
    token: Token
) {
    Card(
        modifier = modifier,
        onClick = { /*TODO*/ }) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = token.symbol)
        }
    }
}