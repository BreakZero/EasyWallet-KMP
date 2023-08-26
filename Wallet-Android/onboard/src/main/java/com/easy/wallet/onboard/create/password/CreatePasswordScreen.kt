package com.easy.wallet.onboard.create.password

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.easy.wallet.onboard.R
import com.easy.wallet.onboard.create.CreateWalletViewModel

@Composable
internal fun CreatePasswordRoute(
    viewModel: CreateWalletViewModel
) {
    CreatePasswordScreen()
}

@Composable
internal fun CreatePasswordScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LinearProgressIndicator(progress = 0.33f, modifier = Modifier.fillMaxWidth())
        Text(
            text = stringResource(id = R.string.create_wallet_create_password),
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            text = stringResource(id = R.string.create_wallet_create_password_desc)
        )
        OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = false, onCheckedChange = {})
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = stringResource(R.string.create_wallet_create_password_terms))
        }
    }
}
