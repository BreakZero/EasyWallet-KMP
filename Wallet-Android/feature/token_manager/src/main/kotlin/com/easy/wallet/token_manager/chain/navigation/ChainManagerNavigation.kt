package com.easy.wallet.token_manager.chain.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.easy.wallet.token_manager.chain.ChainManagerRoute

internal const val chainManagerRouter = "chain_manager_route"

fun NavController.navigateChainManager(navOptions: NavOptions? = null) {
    navigate(chainManagerRouter, navOptions)
}

fun NavGraphBuilder.attachChainManager() {
    composable(chainManagerRouter) {
        ChainManagerRoute()
    }
}
