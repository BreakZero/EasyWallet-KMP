package com.easy.wallet.discover.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.easy.wallet.discover.DiscoverRoute
import kotlinx.serialization.Serializable

@Serializable
internal data object DiscoverTabRoute

@Serializable
data object DiscoverEntryRoute

fun NavController.selectedDiscoverTab(navOptions: NavOptions? = null) {
  navigate(DiscoverTabRoute, navOptions)
}

fun NavGraphBuilder.attachDiscoverTabGraph(nestedGraphs: NavGraphBuilder.() -> Unit) {
  navigation<DiscoverTabRoute>(startDestination = DiscoverEntryRoute) {
    composable<DiscoverEntryRoute> {
      DiscoverRoute()
    }
    nestedGraphs()
  }
}
