package com.easy.wallet.onboard.create.secure

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EasyWalletTheme
import com.easy.wallet.onboard.R
import com.easy.wallet.onboard.components.ProgressTopBar
import com.easy.wallet.onboard.create.CreateWalletEvent
import com.easy.wallet.onboard.create.CreateWalletViewModel

@Composable
internal fun SecureRoute(viewModel: CreateWalletViewModel) {
  SecureScreen(onEvent = viewModel::handleEvent)
}

@Composable
internal fun SecureScreen(onEvent: (CreateWalletEvent) -> Unit) {
  Scaffold(
    topBar = {
      ProgressTopBar(
        modifier = Modifier
          .fillMaxWidth()
          .padding(end = 16.dp),
        step = 2,
        totalSteps = 3,
        navigationIcon = {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
            contentDescription = ""
          )
        },
        action = {
          onEvent(CreateWalletEvent.PopBack)
        }
      )
    },
    bottomBar = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp)
          .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        OutlinedButton(
          modifier = Modifier.fillMaxWidth(),
          onClick = { /*TODO*/ }
        ) {
          Text(text = "Remind Me Later")
        }
        Button(
          modifier = Modifier.fillMaxWidth(),
          onClick = { onEvent(CreateWalletEvent.OnStartInSecure) }
        ) {
          Text(text = "Start")
        }
      }
    }
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(it)
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.SpaceAround
    ) {
      Text(
        text = stringResource(id = R.string.wallet_android_feature_onboard_create_wallet_secure_your_wallet),
        style = MaterialTheme.typography.headlineLarge
      )
      Image(
        modifier = Modifier
          .fillMaxWidth()
          .aspectRatio(ratio = 265.0F / 191.0F),
        painter = painterResource(id = R.drawable.wallet_android_feature_onboard_ic_secure),
        contentDescription = null
      )
      Text(text = stringResource(id = R.string.wallet_android_feature_onboard_create_wallet_secure_desc))
    }
  }
}

@Composable
@ThemePreviews
private fun SecurePreview() {
  EasyWalletTheme {
    Surface(modifier = Modifier.fillMaxSize()) {
      SecureScreen {}
    }
  }
}
