package com.easy.wallet.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.easy.wallet.assetmanager.coin.navigation.attachCoinManagerScreens
import com.easy.wallet.assetmanager.coin.navigation.navigateToCoinEditor
import com.easy.wallet.assetmanager.coin.navigation.navigateToCoinManager
import com.easy.wallet.assetmanager.platform.navigation.attachChainManager
import com.easy.wallet.assetmanager.platform.navigation.navigateToChainDetail
import com.easy.wallet.assetmanager.platform.navigation.navigateToSupportedChains
import com.easy.wallet.discover.navigation.attachDiscoverTabGraph
import com.easy.wallet.home.navigation.WalletTabRoute
import com.easy.wallet.home.navigation.attachWalletTabGraph
import com.easy.wallet.home.navigation.toTransactionList
import com.easy.wallet.marketplace.navigation.attachMarketplaceTabGraph
import com.easy.wallet.news.navigation.attachNewsTabGraph
import com.easy.wallet.onboard.create.navigation.attachCreateWalletGraph
import com.easy.wallet.onboard.create.navigation.toCreateWallet
import com.easy.wallet.onboard.restore.navigation.attachRestoreWalletScreens
import com.easy.wallet.onboard.restore.navigation.toImportWallet
import com.easy.wallet.send.navigation.attachSendFlowGraph
import com.easy.wallet.send.navigation.startSendFlow
import com.easy.wallet.settings.navigation.attachSettingsModule
import com.easy.wallet.settings.navigation.toSettings
import com.easy.wallet.ui.WalletAppState
import kotlinx.coroutines.launch

@Composable
fun WalletNavHost(
  appState: WalletAppState,
  onShowSnackbar: suspend (String, String?) -> Boolean,
  modifier: Modifier = Modifier
) {
  val navController = appState.navController
  val scope = rememberCoroutineScope()
  NavHost(
    modifier = modifier,
    navController = navController,
    startDestination = WalletTabRoute
  ) {
    attachWalletTabGraph(
      onCreateWallet = navController::toCreateWallet,
      onRestoreWallet = navController::toImportWallet,
      navigateToSettings = navController::toSettings,
      onCoinClicked = {
        navController.toTransactionList(it.id)
      },
      onStartSend = { navController.startSendFlow(it) },
      showSnackbar = { scope.launch { onShowSnackbar(it, null) } },
      popBack = navController::popBackStack
    ) {
      attachCreateWalletGraph(navController)
      attachRestoreWalletScreens(navController)

      attachSendFlowGraph(
        navController,
        onShowSnackbar = { scope.launch { onShowSnackbar(it, null) } }
      )

      attachSettingsModule(
        navigateToSupportedChains = navController::navigateToSupportedChains,
        navigateTokenManager = navController::navigateToCoinManager,
        popBack = navController::popBackStack
      ) {
        attachChainManager(
          navigateToDetail = { navController.navigateToChainDetail(it) },
          navigateUp = navController::navigateUp
        )
        attachCoinManagerScreens(
          navigateToEditor = { navController.navigateToCoinEditor() },
          navigateUp = navController::navigateUp
        )
      }
    }

    attachNewsTabGraph {}
    attachMarketplaceTabGraph {}
    attachDiscoverTabGraph {}
  }
}
