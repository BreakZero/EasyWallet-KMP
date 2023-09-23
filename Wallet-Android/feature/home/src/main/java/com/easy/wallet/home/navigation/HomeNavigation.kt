package com.easy.wallet.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.easy.wallet.home.HomeRoute
import com.easy.wallet.home.transactions.TransactionsRoute
import com.easy.wallet.model.token.Token

const val HOME_GRAPH_ROUTE_PATTERN = "home_graph"
const val homeEntryRoute = "_home_route"

internal val transactionListRoute = "_transaction_list_route"

fun NavController.selectedHomeTab(navOptions: NavOptions? = null) {
    this.navigate(HOME_GRAPH_ROUTE_PATTERN, navOptions)
}

fun NavController.toTransactionList(navOptions: NavOptions? = null) {
    this.navigate(transactionListRoute, navOptions)
}

fun NavGraphBuilder.homeGraph(
    onCreateWallet: () -> Unit,
    onRestoreWallet: () -> Unit,
    navigateToSettings: () -> Unit,
    onTokenClick: (Token) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(route = HOME_GRAPH_ROUTE_PATTERN, startDestination = homeEntryRoute) {
        composable(route = homeEntryRoute) {
            HomeRoute(
                onCreateWallet = onCreateWallet,
                onRestoreWallet = onRestoreWallet,
                onTokenClick = onTokenClick,
                navigateToSettings = navigateToSettings,
            )
        }
        composable(route = transactionListRoute) {
            TransactionsRoute()
        }
        nestedGraphs()
    }
}
