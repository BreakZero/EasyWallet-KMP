package com.easy.wallet.onboard.create.password

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun CreatePasswordRoute() {
    val viewModel: CreatePasswordViewModel = koinViewModel()
    CreatePasswordScreen()
}

@Composable
internal fun CreatePasswordScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LinearProgressIndicator(progress = 0.66f)
        Text(text = "Create Password")
    }
}