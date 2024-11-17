package com.easy.wallet.assetmanager.platform.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.easy.wallet.assetmanager.platform.detail.ChainDetailRoute
import com.easy.wallet.assetmanager.platform.editor.ChainEditorRoute
import com.easy.wallet.assetmanager.platform.manager.ChainManagerRoute

internal const val VIEW_SUPPORTED_CHAINS_ROUTE = "supported_chains_route"
internal const val VIEW_CHAIN_DETAIL_ROUTE = "view_chain_detail"
internal const val CHAIN_EDITOR_ROUTE = "chain_editor_route"

fun NavController.navigateToSupportedChains(navOptions: NavOptions? = null) {
  navigate(VIEW_SUPPORTED_CHAINS_ROUTE, navOptions)
}

fun NavController.navigateToChainDetail(platformId: String?, navOptions: NavOptions? = null) {
  navigate("$VIEW_CHAIN_DETAIL_ROUTE?platformId=$platformId", navOptions)
}

fun NavController.navigateChainEditor(platformId: Long, navOptions: NavOptions? = null) {
  navigate("$CHAIN_EDITOR_ROUTE?platformId=$platformId", navOptions)
}

fun NavGraphBuilder.attachChainManager(navigateToDetail: (String?) -> Unit, navigateUp: () -> Unit) {
  composable(VIEW_SUPPORTED_CHAINS_ROUTE) {
    ChainManagerRoute(
      navigateToDetail = navigateToDetail,
      navigateUp = navigateUp
    )
  }

  composable(
    route = "$VIEW_CHAIN_DETAIL_ROUTE?platformId={platformId}",
    arguments = listOf(
      navArgument("platformId") {
        type = NavType.StringType
        nullable = true
        defaultValue = null
      }
    )
  ) {
    ChainDetailRoute(navigateUp = navigateUp)
  }

  composable(
    route = "$CHAIN_EDITOR_ROUTE?platformId={platformId}",
    arguments = listOf(
      navArgument("platformId") {
        type = NavType.StringType
        nullable = true
        defaultValue = null
      }
    )
  ) {
    ChainEditorRoute(
      navigateUp = navigateUp
    )
  }
}
