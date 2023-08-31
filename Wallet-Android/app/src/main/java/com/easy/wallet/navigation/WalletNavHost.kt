package com.easy.wallet.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.easy.wallet.discover.DiscoverScreen
import com.easy.wallet.home.navigation.homeNavigationRoute
import com.easy.wallet.home.navigation.homeScreen
import com.easy.wallet.marketplace.MarketplaceRoute
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
    startDestination: String = homeNavigationRoute,
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
        importWalletScreen()
        composable(TopLevelDestination.MARKETPLACE.name) {
            MarketplaceRoute()
        }
        composable(TopLevelDestination.DISCOVER.name) {
            DiscoverScreen()
        }
    }
}