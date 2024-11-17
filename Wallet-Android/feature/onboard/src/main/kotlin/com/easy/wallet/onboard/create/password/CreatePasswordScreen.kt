package com.easy.wallet.onboard.create.password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EasyWalletTheme
import com.easy.wallet.onboard.R
import com.easy.wallet.onboard.components.PasswordTextField
import com.easy.wallet.onboard.components.ProgressTopBar
import com.easy.wallet.onboard.create.CreateWalletEvent
import com.easy.wallet.onboard.create.CreateWalletViewModel
import com.easy.wallet.onboard.util.PasswordValidateResult

@Composable
internal fun CreatePasswordRoute(viewModel: CreateWalletViewModel) {
  LaunchedEffect(key1 = viewModel) {
    viewModel.validatePassword()
  }
  CreatePasswordScreen(
    passwordTextField = viewModel.passwordTextFieldState,
    confirmPasswordTextFieldState = viewModel.confirmPasswordTextFieldState,
    passwordValidateResult = viewModel.validateResult,
    onEvent = viewModel::handleEvent
  )
}

@Composable
internal fun CreatePasswordScreen(
  passwordTextField: TextFieldState,
  confirmPasswordTextFieldState: TextFieldState,
  passwordValidateResult: PasswordValidateResult,
  onEvent: (CreateWalletEvent) -> Unit
) {
  var isTermChecked by remember {
    mutableStateOf(false)
  }
  Scaffold(
    topBar = {
      ProgressTopBar(
        modifier = Modifier
          .fillMaxWidth()
          .padding(end = 16.dp),
        step = 1,
        totalSteps = 3,
        navigationIcon = {
          Icon(imageVector = Icons.Default.Close, contentDescription = "")
        },
        action = { onEvent(CreateWalletEvent.PopBack) }
      )
    },
    bottomBar = {
      Button(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        enabled = passwordValidateResult == PasswordValidateResult.Pass && isTermChecked,
        onClick = { onEvent(CreateWalletEvent.OnCreatePassword) }
      ) {
        Text(text = stringResource(id = R.string.wallet_android_feature_onboard_create_wallet_create_password))
      }
    }
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(it)
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      Text(
        text = stringResource(id = R.string.wallet_android_feature_onboard_create_wallet_create_password),
        style = MaterialTheme.typography.headlineLarge
      )
      Text(
        modifier = Modifier
          .fillMaxWidth(),
        text = stringResource(id = R.string.wallet_android_feature_onboard_create_wallet_create_password_desc),
        style = MaterialTheme.typography.bodyMedium
      )
      PasswordTextField(
        textField = passwordTextField,
        placeholder = { Text(text = "New password") },
        trailingIcon = {
          Icon(imageVector = Icons.Default.RemoveRedEye, contentDescription = null)
        },
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 12.dp)
      )
      PasswordTextField(
        textField = confirmPasswordTextFieldState,
        placeholder = { Text(text = "Confirm password") },
        modifier = Modifier.fillMaxWidth()
      )
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(IntrinsicSize.Min)
          .padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Checkbox(
          checked = isTermChecked,
          onCheckedChange = {
            isTermChecked = !isTermChecked
          }
        )
        Text(text = stringResource(R.string.wallet_android_feature_onboard_create_wallet_create_password_terms))
      }
    }
  }
}

@Composable
@ThemePreviews
private fun CreatePassword_Preview() {
  EasyWalletTheme {
    Surface(modifier = Modifier.fillMaxSize()) {
      CreatePasswordScreen(
        passwordTextField = rememberTextFieldState(),
        confirmPasswordTextFieldState = rememberTextFieldState(),
        passwordValidateResult = PasswordValidateResult.Pass,
        onEvent = {}
      )
    }
  }
}
