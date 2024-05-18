package com.easy.wallet.marketplace.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.easy.wallet.marketplace.MarketplaceRoute
import kotlinx.serialization.Serializable

@Serializable
internal data object MarketplaceTabRoute

@Serializable
data object MarketplaceEntryRoute

fun NavController.selectedMarketplaceTab(navOptions: NavOptions? = null) = navigate(MarketplaceTabRoute, navOptions)

fun NavGraphBuilder.attachMarketplaceTabGraph(
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation<MarketplaceTabRoute>(startDestination = MarketplaceEntryRoute) {
        composable<MarketplaceEntryRoute> {
            MarketplaceRoute()
        }

        nestedGraphs()
    }
}
