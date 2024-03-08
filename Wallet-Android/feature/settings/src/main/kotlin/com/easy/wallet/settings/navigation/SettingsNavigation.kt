package com.easy.wallet.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.easy.wallet.settings.SettingsRoute

const val settingsRoute = "_route_settings"
internal const val settingsEntryRoute = "_route_settings_entry"

fun NavController.toSettings(navOptions: NavOptions? = null) {
    this.navigate(settingsRoute, navOptions)
}

fun NavGraphBuilder.settingsGraph(
    navigateChainManager: () -> Unit,
    popBack: () -> Unit
) {
    navigation(route = settingsRoute, startDestination = settingsEntryRoute) {
        composable(settingsEntryRoute) {
            SettingsRoute(
                navigateChainManager = navigateChainManager,
                popBack = popBack
            )
        }
    }
}
