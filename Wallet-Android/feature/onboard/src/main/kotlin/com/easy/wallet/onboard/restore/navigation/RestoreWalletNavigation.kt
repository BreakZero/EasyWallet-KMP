package com.easy.wallet.onboard.restore.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.easy.wallet.onboard.restore.RestoreWalletRoute

const val importWalletRoute = "_restore_wallet"

fun NavController.toImportWallet(navOptions: NavOptions? = null) {
    this.navigate(importWalletRoute, navOptions)
}

fun NavGraphBuilder.attachRestoreWallet(navController: NavController) {
    composable(importWalletRoute) {
        RestoreWalletRoute(
            onImportSuccess = navController::popBackStack,
        )
    }
}
