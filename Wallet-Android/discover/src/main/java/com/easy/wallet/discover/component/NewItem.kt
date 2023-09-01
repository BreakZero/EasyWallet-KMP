package com.easy.wallet.discover.component

import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.easy.wallet.model.news.New

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NewItemView(
    modifier: Modifier = Modifier,
    new: New
) {
    Card(onClick = {

    }) {
        Text(modifier = modifier, text = new.title)
    }
}