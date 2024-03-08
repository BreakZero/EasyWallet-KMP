package com.easy.wallet.token_manager.chain.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.easy.wallet.token_manager.chain.editor.ChainEditorRoute
import com.easy.wallet.token_manager.chain.manager.ChainManagerRoute

internal const val chainManagerRouter = "chain_manager_route"
internal const val chainEditorRouter = "chain_editor_route"

fun NavController.navigateChainManager(navOptions: NavOptions? = null) {
    navigate(chainManagerRouter, navOptions)
}

fun NavController.navigateChainEditor(navOptions: NavOptions? = null) {
    navigate(chainEditorRouter, navOptions)
}

fun NavGraphBuilder.attachChainManager(
    navigateToEditor: (Long) -> Unit,
    navigateUp: () -> Unit
) {
    composable(chainManagerRouter) {
        ChainManagerRoute(
            navigateToEditor = navigateToEditor,
            navigateUp = navigateUp
        )
    }

    composable(chainEditorRouter) {
        ChainEditorRoute(navigateUp = navigateUp)
    }
}
