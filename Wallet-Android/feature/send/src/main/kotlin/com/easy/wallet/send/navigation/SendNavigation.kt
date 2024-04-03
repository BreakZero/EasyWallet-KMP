package com.easy.wallet.send.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.easy.wallet.send.SendSharedViewModel
import com.easy.wallet.send.amount.EvmChainSendAmountScreen
import com.easy.wallet.send.amount.SendAmountRoute
import com.easy.wallet.send.destination.EvmChainDestinationScreen
import com.easy.wallet.send.destination.SendDestinationRoute
import com.easy.wallet.send.overview.EvmChainOverviewScreen
import com.easy.wallet.send.overview.TransactionOverviewRoute
import org.koin.androidx.compose.koinViewModel

internal const val sendEnterRoute = "send_enter_route"

internal const val sendAmountRoute = "send_amount_route"
internal const val sendDestinationRoute = "send_destination_route"
internal const val sendOverviewRoute = "send_overview_route"

fun NavController.startSendFlow(navOptions: NavOptions? = null) {
    navigate(sendEnterRoute, navOptions)
}

fun NavGraphBuilder.attachSendGraph(navController: NavController) {
    navigation(startDestination = sendDestinationRoute, route = sendEnterRoute) {
        composable(sendDestinationRoute) {
            val sharedViewModel: SendSharedViewModel = it.sharedViewModel(navController = navController)
            SendDestinationRoute(viewModel = sharedViewModel) {
                navController.navigate(sendAmountRoute)
            }
        }

        composable(sendAmountRoute) {
            val sharedViewModel: SendSharedViewModel = it.sharedViewModel(navController = navController)
            SendAmountRoute(viewModel = sharedViewModel) {
                navController.navigate(sendOverviewRoute)
            }
        }

        composable(sendOverviewRoute) {
            val sharedViewModel: SendSharedViewModel = it.sharedViewModel(navController = navController)
            TransactionOverviewRoute(viewModel = sharedViewModel)
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel()
    val parentEntry = remember(key1 = this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(viewModelStoreOwner = parentEntry)
}
