package com.easy.wallet.onboard.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EasyWalletTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PasswordTextField(
    modifier: Modifier = Modifier,
    textField: TextFieldState,
    isError: Boolean = false,
    textObfuscationMode: TextObfuscationMode = TextObfuscationMode.RevealLastTyped,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    BasicSecureTextField(
        modifier = modifier,
        state = textField,
        textObfuscationMode = textObfuscationMode,
        decorator = @Composable {
            val interactionSource = remember { MutableInteractionSource() }
            TextFieldDefaults.DecorationBox(
                value = textField.text.toString(),
                innerTextField = it,
                enabled = true,
                singleLine = true,
                isError = isError,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource
            )
        }
    )
}

@ThemePreviews
@Composable
private fun PasswordTextField_Preview() {
    EasyWalletTheme {
        PasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            textField = rememberTextFieldState("123")
        )
    }
}
