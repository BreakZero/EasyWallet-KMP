package com.easy.wallet.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.easy.wallet.discover.navigation.attachDiscoverGraph
import com.easy.wallet.home.navigation.HOME_GRAPH_ROUTE_PATTERN
import com.easy.wallet.home.navigation.attachHomeGraph
import com.easy.wallet.home.navigation.toTransactionList
import com.easy.wallet.marketplace.navigation.attachMarketplaceGraph
import com.easy.wallet.news.navigation.attachNewsGraph
import com.easy.wallet.onboard.create.navigation.attachCreateWalletGraph
import com.easy.wallet.onboard.create.navigation.toCreateWallet
import com.easy.wallet.onboard.restore.navigation.attachRestoreWallet
import com.easy.wallet.onboard.restore.navigation.toImportWallet
import com.easy.wallet.settings.navigation.attachSettingsModule
import com.easy.wallet.settings.navigation.toSettings
import com.easy.wallet.token_manager.chain.navigation.attachChainManager
import com.easy.wallet.token_manager.chain.navigation.navigateChainEditor
import com.easy.wallet.token_manager.chain.navigation.navigateToSupportedChains
import com.easy.wallet.token_manager.token.navigation.attachTokenManager
import com.easy.wallet.token_manager.token.navigation.navigateToTokenEditor
import com.easy.wallet.token_manager.token.navigation.navigateToTokenManager
import com.easy.wallet.ui.WalletAppState

@Composable
fun WalletNavHost(
    appState: WalletAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = HOME_GRAPH_ROUTE_PATTERN
) {
    val navController = appState.navController
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        attachHomeGraph(
            onCreateWallet = navController::toCreateWallet,
            onRestoreWallet = navController::toImportWallet,
            navigateToSettings = navController::toSettings,
            onTokenClick = {
                navController.toTransactionList(it.id)
            },
            navigateUp = navController::navigateUp
        ) {

        }
        attachCreateWalletGraph(navController)
        attachRestoreWallet(navController)
        attachNewsGraph()
        attachMarketplaceGraph()
        attachDiscoverGraph()
        attachSettingsModule(
            navigateToSupportedChains = navController::navigateToSupportedChains,
            navigateTokenManager = navController::navigateToTokenManager,
            popBack = navController::popBackStack
        )
        attachChainManager(
            navigateToEditor = { navController.navigateChainEditor(it) },
            navigateUp = navController::navigateUp
        )
        attachTokenManager(
            navigateToEditor = { navController.navigateToTokenEditor() },
            navigateUp = navController::navigateUp
        )
    }
}
