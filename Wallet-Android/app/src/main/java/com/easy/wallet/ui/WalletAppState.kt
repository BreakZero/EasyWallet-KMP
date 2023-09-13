package com.easy.wallet.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.easy.wallet.discover.navigation.discoverTabRoute
import com.easy.wallet.discover.navigation.selectedDiscoverTab
import com.easy.wallet.home.navigation.homeTabRoute
import com.easy.wallet.home.navigation.selectedHomeTab
import com.easy.wallet.marketplace.navigation.marketplaceTabRoute
import com.easy.wallet.marketplace.navigation.selectedMarketplaceTab
import com.easy.wallet.navigation.TopLevelDestination
import com.easy.wallet.news.navigation.newsTabRoute
import com.easy.wallet.news.navigation.selectedNewsTab
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAppState(
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
): WalletAppState {
    return remember(
        navController,
        coroutineScope,
        windowSizeClass,
    ) {
        WalletAppState(
            navController,
            coroutineScope,
            windowSizeClass,
        )
    }
}

@Stable
class WalletAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    private val windowSizeClass: WindowSizeClass
) {
    val shouldShowNavRail: Boolean
        get() = windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact

    val shouldShowBottomBar: Boolean
        @Composable get() = !shouldShowNavRail &&
            (currentTopLevelDestination != null && currentTopLevelDestination in topLevelDestinations)

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            homeTabRoute -> TopLevelDestination.HOME
            newsTabRoute -> TopLevelDestination.NEWS
            discoverTabRoute -> TopLevelDestination.DISCOVER
            marketplaceTabRoute -> TopLevelDestination.MARKETPLACE
            else -> null
        }

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.HOME -> navController.selectedHomeTab(topLevelNavOptions)
            TopLevelDestination.NEWS -> navController.selectedNewsTab(topLevelNavOptions)
            TopLevelDestination.MARKETPLACE -> navController.selectedMarketplaceTab(topLevelNavOptions)
            TopLevelDestination.DISCOVER -> navController.selectedDiscoverTab(topLevelNavOptions)
        }
    }
}
