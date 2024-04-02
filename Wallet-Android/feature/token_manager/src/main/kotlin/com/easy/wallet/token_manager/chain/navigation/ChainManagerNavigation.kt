package com.easy.wallet.token_manager.chain.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.easy.wallet.token_manager.chain.detail.ChainDetailRoute
import com.easy.wallet.token_manager.chain.editor.ChainEditorRoute
import com.easy.wallet.token_manager.chain.manager.ChainManagerRoute

internal const val viewSupportedChainsRoute = "supported_chains_route"
internal const val viewChainDetailRoute = "view_chain_detail"
internal const val chainEditorRouter = "chain_editor_route"

fun NavController.navigateToSupportedChains(navOptions: NavOptions? = null) {
    navigate(viewSupportedChainsRoute, navOptions)
}

fun NavController.navigateToChainDetail(chainId: Long, navOptions: NavOptions? = null) {
    navigate("${viewChainDetailRoute}?chainId=$chainId", navOptions)
}

fun NavController.navigateChainEditor(chainId: Long, navOptions: NavOptions? = null) {
    navigate("${chainEditorRouter}?chainId=$chainId", navOptions)
}

fun NavGraphBuilder.attachChainManager(
    navigateToDetail: (Long) -> Unit,
    navigateUp: () -> Unit
) {
    composable(viewSupportedChainsRoute) {
        ChainManagerRoute(
            navigateToDetail = navigateToDetail,
            navigateUp = navigateUp
        )
    }

    composable(
        route = "${viewChainDetailRoute}?chainId={chainId}",
        arguments = listOf(navArgument("chainId") {
            type = NavType.LongType
            defaultValue = -1L
        })
    ) {
        ChainDetailRoute(navigateUp = navigateUp)
    }

    composable(
        route = "${chainEditorRouter}?chainId={chainId}",
        arguments = listOf(navArgument("chainId") {
            type = NavType.LongType
            defaultValue = -1L
        })
    ) {
        ChainEditorRoute(
            navigateUp = navigateUp
        )
    }
}
