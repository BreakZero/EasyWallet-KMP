package com.easy.wallet.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.easy.wallet.discover.navigation.discoverTabScreen
import com.easy.wallet.home.navigation.homeScreen
import com.easy.wallet.home.navigation.homeTabRoute
import com.easy.wallet.marketplace.navigation.marketplaceTabScreen
import com.easy.wallet.news.navigation.newsTabScreen
import com.easy.wallet.onboard.create.navigation.createGraph
import com.easy.wallet.onboard.create.navigation.toCreateWallet
import com.easy.wallet.onboard.restore.navigation.importWalletScreen
import com.easy.wallet.onboard.restore.navigation.toImportWallet
import com.easy.wallet.ui.WalletAppState

@Composable
fun WalletNavHost(
    appState: WalletAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = homeTabRoute,
) {
    val navController = appState.navController
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        homeScreen(
            onCreateWallet = navController::toCreateWallet,
            onRestoreWallet = navController::toImportWallet
        )
        createGraph(navController)
        importWalletScreen(navController)
        newsTabScreen()
        marketplaceTabScreen()
        discoverTabScreen()
    }
}