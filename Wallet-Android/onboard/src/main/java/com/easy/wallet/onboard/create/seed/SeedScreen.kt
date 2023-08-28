package com.easy.wallet.onboard.create.seed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.component.ThemePreviews
import com.easy.wallet.design.ui.EWalletTheme
import com.easy.wallet.onboard.R
import com.easy.wallet.onboard.create.component.TopBar

@Composable
internal fun SeedRoute() {

}

@Composable
internal fun SeedScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        TopBar(step = 3, totalStep = 3, navigationIcon = {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
        }) {

        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.create_wallet_secure_your_wallet),
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(48.dp))
        Text(text = stringResource(id = R.string.create_wallet_secure_desc))
        Spacer(modifier = Modifier.height(12.dp))

        val words by remember {
            mutableStateOf((1..12).map {
                "word-$it"
            })
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .blur(6.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(12.dp)
            ) {
                items(words, key = {
                    it
                }) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(MaterialTheme.colorScheme.background)
                            .padding(vertical = 8.dp),
                        text = it,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(imageVector = Icons.Default.RemoveRedEye, contentDescription = null)
                Text(text = "Tap to reveal your seed phrase")
            }
        }
    }
}

@ThemePreviews
@Composable
private fun SeedScreen_Preview() {
    EWalletTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SeedScreen()
        }
    }
}