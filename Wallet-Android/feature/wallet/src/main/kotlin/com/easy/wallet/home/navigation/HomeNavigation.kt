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
import com.easy.wallet.home.WalletRoute
import com.easy.wallet.home.transactions.TransactionsRoute
import com.easy.wallet.model.asset.CoinModel
import java.net.URLDecoder
import java.net.URLEncoder
import kotlin.text.Charsets.UTF_8

const val HOME_GRAPH_ROUTE_PATTERN = "wallet_graph"
const val homeEntryRoute = "_wallet_route"

internal const val transactionListRoute = "_transaction_list_route"

fun NavController.selectedHomeTab(navOptions: NavOptions? = null) {
    this.navigate(HOME_GRAPH_ROUTE_PATTERN, navOptions)
}

@VisibleForTesting
internal const val COIN_ID_ARG = "tokenId"

internal class CoinArgs(val tokenId: String) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        URLDecoder.decode(
            savedStateHandle[COIN_ID_ARG],
            UTF_8.name()
        )
    )
}

fun NavController.toTransactionList(coinId: String, navOptions: NavOptions? = null) {
    val encodeTokenId = URLEncoder.encode(coinId, UTF_8.name())
    this.navigate("$transactionListRoute/$encodeTokenId", navOptions)
}

fun NavGraphBuilder.attachHomeGraph(
    onCreateWallet: () -> Unit,
    onRestoreWallet: () -> Unit,
    navigateToSettings: () -> Unit,
    onCoinClicked: (CoinModel) -> Unit,
    onStartSend: (String) -> Unit,
    navigateUp: () -> Unit,
    showSnackbar: (String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(route = HOME_GRAPH_ROUTE_PATTERN, startDestination = homeEntryRoute) {
        composable(route = homeEntryRoute) {
            WalletRoute(
                onCreateWallet = onCreateWallet,
                onRestoreWallet = onRestoreWallet,
                onTokenClick = onCoinClicked,
                navigateToSettings = navigateToSettings,
            )
        }
        composable(
            route = "$transactionListRoute/{$COIN_ID_ARG}",
            arguments = listOf(
                navArgument(COIN_ID_ARG) { type = NavType.StringType }
            )
        ) {
            TransactionsRoute(
                startToSend = onStartSend,
                showSnackbar = showSnackbar,
                navigateUp = navigateUp
            )
        }
        nestedGraphs()
    }
}
