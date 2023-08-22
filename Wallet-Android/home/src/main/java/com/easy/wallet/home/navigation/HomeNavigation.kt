package com.easy.wallet.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.easy.wallet.home.HomeRoute

const val homeNavigationRoute = "_home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeNavigationRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    onCreateWallet: () -> Unit,
    onRestoreWallet: () -> Unit
) {
    composable(route = homeNavigationRoute) {
        HomeRoute(onCreateWallet = onCreateWallet, onRestoreWallet = onRestoreWallet)
    }
}