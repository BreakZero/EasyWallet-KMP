package com.easy.wallet.assetmanager.coin.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.easy.wallet.assetmanager.coin.editor.TokenEditorRoute
import com.easy.wallet.assetmanager.coin.manager.TokenManagerRoute
import kotlinx.serialization.Serializable

@Serializable
internal data object CoinManagerRoute

@Serializable
internal data object CoinEditorRoute

fun NavController.navigateToCoinManager(navOptions: NavOptions? = null) = navigate(CoinManagerRoute, navOptions)

fun NavController.navigateToCoinEditor(navOptions: NavOptions? = null) = navigate(CoinEditorRoute, navOptions)

fun NavGraphBuilder.attachCoinManagerScreens(navigateToEditor: (String?) -> Unit, navigateUp: () -> Unit) {
  composable<CoinManagerRoute> {
    TokenManagerRoute(
      navigateToEditor = navigateToEditor,
      navigateUp = navigateUp
    )
  }
  composable<CoinEditorRoute> {
    TokenEditorRoute(navigateUp = navigateUp)
  }
}
