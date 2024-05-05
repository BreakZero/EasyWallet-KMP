package com.easy.wallet.home.navigation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.easy.wallet.home.HomeRoute
import com.easy.wallet.home.transactions.TransactionsRoute
import com.easy.wallet.model.asset.CoinModel
import java.net.URLDecoder
import java.net.URLEncoder
import kotlin.text.Charsets.UTF_8

const val HOME_GRAPH_ROUTE_PATTERN = "home_graph"
const val homeEntryRoute = "_home_route"

internal const val transactionListRoute = "_transaction_list_route"

fun NavController.selectedHomeTab(navOptions: NavOptions? = null) {
    this.navigate(HOME_GRAPH_ROUTE_PATTERN, navOptions)
}

private val URL_CHARACTER_ENCODING = UTF_8.name()

@VisibleForTesting
internal const val COIN_ID_ARG = "coinId"
internal const val PLATFORM_ID_ARG = "platformId"

internal class CoinArgs(val coinId: String, val platformId: String) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        URLDecoder.decode(
            savedStateHandle[COIN_ID_ARG],
            URL_CHARACTER_ENCODING
        ),
        URLDecoder.decode(
            savedStateHandle[PLATFORM_ID_ARG],
            URL_CHARACTER_ENCODING
        )
    )
}

fun NavController.toTransactionList(
    coinId: String,
    platformId: String,
    navOptions: NavOptionsBuilder.() -> Unit = {}
) {
    val encodedCoinId = URLEncoder.encode(coinId, URL_CHARACTER_ENCODING)
    val encodedPlatformId = URLEncoder.encode(platformId, URL_CHARACTER_ENCODING)

    val transactionListRoute = "$transactionListRoute/$encodedCoinId/$encodedPlatformId"
    navigate(transactionListRoute) {
        navOptions()
    }
}

fun NavGraphBuilder.attachHomeGraph(
    onCreateWallet: () -> Unit,
    onRestoreWallet: () -> Unit,
    navigateToSettings: () -> Unit,
    onCoinClicked: (CoinModel) -> Unit,
    onStartSend: (CoinModel) -> Unit,
    navigateUp: () -> Unit,
    showSnackbar: (String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(route = HOME_GRAPH_ROUTE_PATTERN, startDestination = homeEntryRoute) {
        composable(route = homeEntryRoute) {
            HomeRoute(
                onCreateWallet = onCreateWallet,
                onRestoreWallet = onRestoreWallet,
                onTokenClick = onCoinClicked,
                navigateToSettings = navigateToSettings,
            )
        }
        composable(
            route = "$transactionListRoute/{$COIN_ID_ARG}/{$PLATFORM_ID_ARG}",
            arguments = listOf(
                navArgument(COIN_ID_ARG) { type = NavType.StringType },
                navArgument(PLATFORM_ID_ARG) { type = NavType.StringType }
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
