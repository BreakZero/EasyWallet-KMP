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

fun NavController.navigateToChainDetail(platformId: String?, navOptions: NavOptions? = null) {
    navigate("${viewChainDetailRoute}?platformId=$platformId", navOptions)
}

fun NavController.navigateChainEditor(platformId: Long, navOptions: NavOptions? = null) {
    navigate("${chainEditorRouter}?platformId=$platformId", navOptions)
}

fun NavGraphBuilder.attachChainManager(
    navigateToDetail: (String?) -> Unit,
    navigateUp: () -> Unit
) {
    composable(viewSupportedChainsRoute) {
        ChainManagerRoute(
            navigateToDetail = navigateToDetail,
            navigateUp = navigateUp
        )
    }

    composable(
        route = "${viewChainDetailRoute}?platformId={platformId}",
        arguments = listOf(navArgument("platformId") {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        ChainDetailRoute(navigateUp = navigateUp)
    }

    composable(
        route = "${chainEditorRouter}?platformId={platformId}",
        arguments = listOf(navArgument("platformId") {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        ChainEditorRoute(
            navigateUp = navigateUp
        )
    }
}
