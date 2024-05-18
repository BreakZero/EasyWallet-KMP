package com.easy.wallet.onboard.restore.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.easy.wallet.onboard.restore.RestoreWalletRoute
import kotlinx.serialization.Serializable

@Serializable
internal data object ImportWalletRoute

fun NavController.toImportWallet(navOptions: NavOptions? = null) {
    this.navigate(ImportWalletRoute, navOptions)
}

fun NavGraphBuilder.attachRestoreWalletScreens(navController: NavController) {
    composable<ImportWalletRoute> {
        RestoreWalletRoute(
            onImportSuccess = navController::popBackStack,
        )
    }
}
