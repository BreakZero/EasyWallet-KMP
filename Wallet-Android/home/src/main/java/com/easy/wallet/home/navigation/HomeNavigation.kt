package com.easy.wallet.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.easy.wallet.home.HomeRoute

const val homeTabRoute = "_home_route"

fun NavController.selectedHomeTab(navOptions: NavOptions? = null) {
    this.navigate(homeTabRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    onCreateWallet: () -> Unit,
    onRestoreWallet: () -> Unit,
    navigateToSettings: () -> Unit
) {
    composable(route = homeTabRoute) {
        HomeRoute(
            onCreateWallet = onCreateWallet,
            onRestoreWallet = onRestoreWallet,
            navigateToSettings = navigateToSettings
        )
    }
}