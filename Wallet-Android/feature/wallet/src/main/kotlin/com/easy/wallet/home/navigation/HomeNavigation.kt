package com.easy.wallet.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.easy.wallet.home.WalletRoute
import com.easy.wallet.home.transactions.TransactionsRoute
import com.easy.wallet.home.transactions.TransactionsViewModel
import com.easy.wallet.model.asset.CoinModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
data object WalletTabRoute

@Serializable
data object WalletEntryRoute


@Serializable
internal data class TransactionListRoute(
    val coinId: String
)

fun NavController.onSelectedWalletTab(navOptions: NavOptions? = null) =
    navigate(WalletTabRoute, navOptions)

fun NavController.toTransactionList(coinId: String, navOptions: NavOptions? = null) =
    navigate(TransactionListRoute(coinId = coinId), navOptions)

fun NavGraphBuilder.attachWalletTabGraph(
    onCreateWallet: () -> Unit,
    onRestoreWallet: () -> Unit,
    navigateToSettings: () -> Unit,
    onCoinClicked: (CoinModel) -> Unit,
    onStartSend: (String) -> Unit,
    popBack: () -> Unit,
    showSnackbar: (String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation<WalletTabRoute>(startDestination = WalletEntryRoute) {
        composable<WalletEntryRoute> {
            WalletRoute(
                onCreateWallet = onCreateWallet,
                onRestoreWallet = onRestoreWallet,
                onCoinItemClick = onCoinClicked,
                navigateToSettings = navigateToSettings,
            )
        }
        composable<TransactionListRoute> {
            val args = it.toRoute<TransactionListRoute>()
            val viewModel: TransactionsViewModel = koinViewModel { parametersOf(args.coinId) }
            TransactionsRoute(
                viewModel = viewModel,
                startToSend = onStartSend,
                showSnackbar = showSnackbar,
                navigateUp = popBack
            )
        }
        nestedGraphs()
    }
}
