package com.easy.wallet.home.navigation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.easy.wallet.home.HomeRoute
import com.easy.wallet.home.transactions.TransactionsRoute
import com.easy.wallet.model.TokenInformation
import java.net.URLDecoder
import java.net.URLEncoder
import kotlin.text.Charsets.UTF_8

const val HOME_GRAPH_ROUTE_PATTERN = "home_graph"
const val homeEntryRoute = "_home_route"

internal const val transactionListRoute = "_transaction_list_route"

fun NavController.selectedHomeTab(navOptions: NavOptions? = null) {
    this.navigate(HOME_GRAPH_ROUTE_PATTERN, navOptions)
}

@VisibleForTesting
internal const val TOKEN_ID_ARG = "tokenId"

internal class TokenArgs(val tokenId: String) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        URLDecoder.decode(
            savedStateHandle[TOKEN_ID_ARG],
            UTF_8.name()
        )
    )
}

fun NavController.toTransactionList(tokenId: String, navOptions: NavOptions? = null) {
    val encodeTokenId = URLEncoder.encode(tokenId, UTF_8.name())
    this.navigate("$transactionListRoute/$encodeTokenId", navOptions)
}

fun NavGraphBuilder.homeGraph(
    onCreateWallet: () -> Unit,
    onRestoreWallet: () -> Unit,
    navigateToSettings: () -> Unit,
    onTokenClick: (TokenInformation) -> Unit,
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
        composable(
            route = "$transactionListRoute/{$TOKEN_ID_ARG}",
            arguments = listOf(
                navArgument(TOKEN_ID_ARG) { type = NavType.StringType }
            )
        ) {
            TransactionsRoute()
        }
        nestedGraphs()
    }
}
