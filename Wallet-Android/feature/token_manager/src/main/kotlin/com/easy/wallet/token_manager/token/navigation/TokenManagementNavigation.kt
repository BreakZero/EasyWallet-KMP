package com.easy.wallet.token_manager.token.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.easy.wallet.token_manager.token.editor.TokenEditorRoute
import com.easy.wallet.token_manager.token.manager.TokenManagerRoute

internal const val tokenManagerRoute = "token_manager_route"
internal const val tokenEditorRoute = "token_editor_route"

fun NavController.navigateToTokenManager(navOptions: NavOptions? = null) {
    navigate(tokenManagerRoute, navOptions)
}

fun NavController.navigateToTokenEditor(navOptions: NavOptions? = null) {
    navigate(tokenEditorRoute, navOptions)
}

fun NavGraphBuilder.attachTokenManager(
    navigateToEditor: (String?) -> Unit,
    navigateUp: () -> Unit
) {
    composable(tokenManagerRoute) {
        TokenManagerRoute(
            navigateToEditor = navigateToEditor,
            navigateUp = navigateUp
        )
    }

    composable(tokenEditorRoute) {
        TokenEditorRoute()
    }
}
