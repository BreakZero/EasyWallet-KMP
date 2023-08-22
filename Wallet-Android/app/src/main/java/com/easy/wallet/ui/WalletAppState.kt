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
import com.easy.wallet.home.navigation.homeNavigationRoute
import com.easy.wallet.home.navigation.navigateToHome
import com.easy.wallet.navigation.TopLevelDestination
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
        windowSizeClass
    ) {
        WalletAppState(
            navController,
            coroutineScope,
            windowSizeClass
        )
    }
}

@Stable
class WalletAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    private val windowSizeClass: WindowSizeClass
) {
    val shouldShowBottomBar: Boolean
        @Composable get() = (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) &&
                (currentTopLevelDestination != null && currentTopLevelDestination in topLevelDestinations)

    val shouldShowNavRail: Boolean
        get() = windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            homeNavigationRoute -> TopLevelDestination.HOME
            TopLevelDestination.DISCOVER.name -> TopLevelDestination.DISCOVER
            TopLevelDestination.MARKETPLACE.name -> TopLevelDestination.MARKETPLACE
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
            TopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)

            TopLevelDestination.MARKETPLACE -> navController.navigate(
                TopLevelDestination.MARKETPLACE.name,
                navOptions = topLevelNavOptions
            )
            TopLevelDestination.DISCOVER -> navController.navigate(
                TopLevelDestination.DISCOVER.name,
                navOptions = topLevelNavOptions
            )
        }
    }
}