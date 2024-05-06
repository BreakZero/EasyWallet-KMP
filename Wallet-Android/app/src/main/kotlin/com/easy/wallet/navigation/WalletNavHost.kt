package com.easy.wallet.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.easy.wallet.send.navigation.attachSendGraph
import com.easy.wallet.send.navigation.startSendFlow
import com.easy.wallet.settings.navigation.attachSettingsModule
import com.easy.wallet.settings.navigation.toSettings
import com.easy.wallet.assetmanager.platform.navigation.attachChainManager
import com.easy.wallet.assetmanager.platform.navigation.navigateToChainDetail
import com.easy.wallet.assetmanager.platform.navigation.navigateToSupportedChains
import com.easy.wallet.assetmanager.coin.navigation.attachTokenManager
import com.easy.wallet.assetmanager.coin.navigation.navigateToTokenEditor
import com.easy.wallet.assetmanager.coin.navigation.navigateToTokenManager
import com.easy.wallet.ui.WalletAppState
import kotlinx.coroutines.launch

@Composable
fun WalletNavHost(
    appState: WalletAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = HOME_GRAPH_ROUTE_PATTERN
) {
    val navController = appState.navController
    val scope = rememberCoroutineScope()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        attachHomeGraph(
            onCreateWallet = navController::toCreateWallet,
            onRestoreWallet = navController::toImportWallet,
            navigateToSettings = navController::toSettings,
            onCoinClicked = {
                navController.toTransactionList(it.id)
            },
            onStartSend = { navController.startSendFlow(it) },
            showSnackbar = { scope.launch { onShowSnackbar(it, null) } },
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
            navigateToDetail = { navController.navigateToChainDetail(it) },
            navigateUp = navController::navigateUp
        )
        attachTokenManager(
            navigateToEditor = { navController.navigateToTokenEditor() },
            navigateUp = navController::navigateUp
        )
        attachSendGraph(navController, onShowSnackbar = { scope.launch { onShowSnackbar(it, null) } })
    }
}
