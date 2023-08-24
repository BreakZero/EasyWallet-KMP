package com.easy.wallet.onboard.create.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.easy.wallet.onboard.create.CreateWalletViewModel
import com.easy.wallet.onboard.create.password.CreatePasswordRoute
import com.easy.wallet.onboard.sharedViewModel

const val createWalletRoute = "_create_wallet_route"
internal const val createPasswordRoute = "_create_password_route"

fun NavController.toCreateWallet(navOptions: NavOptions? = null) {
    this.navigate(createWalletRoute, navOptions)
}

fun NavGraphBuilder.createGraph(navController: NavController) {
    navigation(route = createWalletRoute, startDestination = createPasswordRoute) {
        composable(route = createPasswordRoute) {
            val viewModel: CreateWalletViewModel = it.sharedViewModel(navController = navController)
            CreatePasswordRoute(viewModel)
        }
    }
}