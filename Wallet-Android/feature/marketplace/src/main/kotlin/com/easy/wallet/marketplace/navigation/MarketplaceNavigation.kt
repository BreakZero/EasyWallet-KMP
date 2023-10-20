package com.easy.wallet.marketplace.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.easy.wallet.marketplace.MarketplaceRoute

const val marketplaceTabRoute = "_marketplace_tab"

fun NavController.selectedMarketplaceTab(navOptions: NavOptions? = null) {
    this.navigate(marketplaceTabRoute, navOptions)
}

fun NavGraphBuilder.marketplaceTabScreen() {
    composable(route = marketplaceTabRoute) {
        MarketplaceRoute()
    }
}