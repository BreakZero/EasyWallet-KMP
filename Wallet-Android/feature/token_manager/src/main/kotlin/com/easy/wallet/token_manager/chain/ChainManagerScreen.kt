package com.easy.wallet.token_manager.chain

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.InputTransformation
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EasyWalletTheme

@Composable
internal fun ChainManagerRoute() {
    ChainManagerScreen()
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun ChainManagerScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { }, navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            })
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 12.dp),
                onClick = { /*TODO*/ }) {
                Text(text = "Save")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            val modifier = Modifier
                .fillMaxWidth()

            EditorWithLabel(modifier = modifier, label = "Name")
            EditorWithLabel(modifier = modifier, label = "RPC Url")
            EditorWithLabel(modifier = modifier, label = "Chain ID")
            EditorWithLabel(modifier = modifier, label = "Website")
            EditorWithLabel(modifier = modifier, label = "Explorer")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun EditorWithLabel(
    modifier: Modifier = Modifier,
    inputTransformation: InputTransformation? = null,
    label: String
) {
    val textFieldState = rememberTextFieldState()
    textFieldState.edit {
        println(this.asCharSequence())
    }
    Row(
        modifier = modifier.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label)
        Spacer(modifier = Modifier.width(12.dp))
        BasicTextField2(
            modifier = Modifier
                .weight(1.0f)
                .padding(horizontal = 8.dp),
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.End
            ),
            inputTransformation = inputTransformation,
            lineLimits = TextFieldLineLimits.SingleLine,
            state = textFieldState
        )
    }
}

@ThemePreviews
@Composable
private fun Editor_Preview() {
    EasyWalletTheme {
        Surface(
            modifier = Modifier.fillMaxWidth()
        ) {
            ChainManagerScreen()
        }
    }
}
