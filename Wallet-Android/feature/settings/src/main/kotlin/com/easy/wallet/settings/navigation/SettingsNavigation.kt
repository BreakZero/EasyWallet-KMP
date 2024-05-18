package com.easy.wallet.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.easy.wallet.settings.SettingsRoute
import kotlinx.serialization.Serializable

@Serializable
internal data object SettingsEntryPointRoute

@Serializable
internal data object SettingsRoute

fun NavController.toSettings(navOptions: NavOptions? = null) {
    this.navigate(SettingsEntryPointRoute, navOptions)
}

fun NavGraphBuilder.attachSettingsModule(
    navigateToSupportedChains: () -> Unit,
    navigateTokenManager: () -> Unit,
    popBack: () -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation<SettingsEntryPointRoute>(startDestination = SettingsRoute) {
        composable<SettingsRoute> {
            SettingsRoute(
                navigateToSupportedChains = navigateToSupportedChains,
                navigateTokenManager = navigateTokenManager,
                popBack = popBack
            )
        }
        nestedGraphs()
    }
}
