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

const val createWalletRoute = "_create_wallet_route"
internal const val createPasswordRoute = "_create_password_route"
internal const val secureRoute = "_secure_route"
internal const val checkSeedRoute = "_handle_seed_route"

fun NavController.toCreateWallet(navOptions: NavOptions? = null) {
    this.navigate(createWalletRoute, navOptions)
}

internal fun NavController.toSecure(navOptions: NavOptions? = null) {
    this.navigate(secureRoute, navOptions)
}
internal fun NavController.toCheckSeed(navOptions: NavOptions? = null) {
    this.navigate(checkSeedRoute, navOptions)
}

fun NavGraphBuilder.attachCreateWalletGraph(navController: NavController) {
    navigation(route = createWalletRoute, startDestination = createPasswordRoute) {
        composable(route = createPasswordRoute) {
            val viewModel: CreateWalletViewModel = it.sharedViewModel(navController = navController)
            CreatePasswordRoute(
                viewModel = viewModel,
                nextToSecure = navController::toSecure,
                onClose = navController::popBackStack,
            )
        }
        composable(route = secureRoute) {
            val viewModel: CreateWalletViewModel = it.sharedViewModel(navController = navController)
            SecureRoute(viewModel = viewModel, nextToCheckSeed = navController::toCheckSeed)
        }

        composable(route = checkSeedRoute) {
            val viewModel: CreateWalletViewModel = it.sharedViewModel(navController = navController)
            SeedPhraseRoute(viewModel, onCreateSuccess = {
                navController.popBackStack(createWalletRoute, true)
            })
        }
    }
}
