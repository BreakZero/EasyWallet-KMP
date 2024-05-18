package com.easy.wallet.onboard.create.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.easy.wallet.onboard.create.CreateWalletViewModel
import com.easy.wallet.onboard.create.password.CreatePasswordRoute
import com.easy.wallet.onboard.create.secure.SecureRoute
import com.easy.wallet.onboard.create.seed.SeedPhraseRoute
import com.easy.wallet.onboard.sharedViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data object WalletEntryPointRoute

@Serializable
internal data object CreateWalletRoute

@Serializable
internal data object CheckSecureRoute
@Serializable
internal data object CheckSeedRoute

fun NavController.toCreateWallet(navOptions: NavOptions? = null) {
    this.navigate(CreateWalletRoute, navOptions)
}

internal fun NavController.toSecure(navOptions: NavOptions? = null) {
    this.navigate(CheckSecureRoute, navOptions)
}
internal fun NavController.toCheckSeed(navOptions: NavOptions? = null) {
    this.navigate(CheckSeedRoute, navOptions)
}

fun NavGraphBuilder.attachCreateWalletGraph(navController: NavController) {
    navigation<WalletEntryPointRoute>(startDestination = CreateWalletRoute) {
        composable<CreateWalletRoute> {
            val viewModel: CreateWalletViewModel = it.sharedViewModel(navController = navController)
            CreatePasswordRoute(
                viewModel = viewModel,
                nextToSecure = navController::toSecure,
                onClose = navController::popBackStack,
            )
        }
        composable<CheckSecureRoute> {
            val viewModel: CreateWalletViewModel = it.sharedViewModel(navController = navController)
            SecureRoute(viewModel = viewModel, nextToCheckSeed = navController::toCheckSeed)
        }

        composable<CheckSeedRoute> {
            val viewModel: CreateWalletViewModel = it.sharedViewModel(navController = navController)
            SeedPhraseRoute(viewModel, onCreateSuccess = {
                navController.popBackStack(WalletEntryPointRoute, true)
            })
        }
    }
}
