package com.easy.wallet.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.easy.wallet.design.component.LoadingState
import com.easy.wallet.design.component.WebView
import com.easy.wallet.design.component.rememberWebViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NewsDetailScreen(
    url: String,
    popBack: () -> Unit
) {
    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = popBack) {
                        Icon(imageVector = Icons.Default.ArrowBackIos, contentDescription = "")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) {
        val webViewState = rememberWebViewState(url = url)
        val loadingState = webViewState.loadingState
        Column(
            modifier = Modifier.padding(it)
        ) {
            if (loadingState is LoadingState.Loading) {
                LinearProgressIndicator(
                    progress = loadingState.progress,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            WebView(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.0f),
                onCreated = { webView ->
                    webView.settings.run {
                        javaScriptEnabled = true
//                        domStorageEnabled = true
//                        databaseEnabled = true
//                        useWideViewPort = true
//                        loadWithOverviewMode = true
                    }
                },
                state = webViewState
            )
        }
    }
}