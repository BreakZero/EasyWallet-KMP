package com.easy.wallet.discover.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.easy.wallet.discover.DiscoverRoute

const val discoverTabRoute = "discover_tab"
internal const val discoverEntryRoute = "discover_entry"

fun NavController.navigateToDiscover(navOptions: NavOptions? = null) {
    this.navigate(discoverTabRoute, navOptions)
}

fun NavGraphBuilder.discoverScreen() {
    composable(discoverTabRoute) {
        DiscoverRoute()
    }
}