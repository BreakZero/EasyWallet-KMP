package com.easy.wallet.onboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EasyWalletTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MnemonicInputView(
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    textFieldState: TextFieldState = rememberTextFieldState(),
    placeholder: @Composable (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 120.dp)
            .background(MaterialTheme.colorScheme.background)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(4.dp)
            )

    ) {
        BasicTextField(
            modifier = Modifier.fillMaxWidth(),
            state = textFieldState,
            textStyle = MaterialTheme.typography.titleMedium,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            decorator = @Composable {
                val interactionSource = remember { MutableInteractionSource() }
                TextFieldDefaults.DecorationBox(
                    value = textFieldState.text.toString(),
                    innerTextField = it,
                    enabled = true,
                    isError = isError,
                    singleLine = false,
                    placeholder = placeholder,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    container = {},
                )
            }
        )
    }

}

@ThemePreviews
@Composable
private fun MnemonicInputView_Preview() {
    EasyWalletTheme {
        MnemonicInputView(
            modifier = Modifier.fillMaxWidth(),
            textFieldState = rememberTextFieldState(),
            placeholder = {
                Text(text = "Enter Seed")
            }
        )
    }
}
