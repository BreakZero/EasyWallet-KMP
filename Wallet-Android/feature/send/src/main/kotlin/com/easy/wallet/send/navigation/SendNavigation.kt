package com.easy.wallet.send.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.easy.wallet.send.SendSharedViewModel
import com.easy.wallet.send.amount.SendAmountRoute
import com.easy.wallet.send.destination.SendDestinationRoute
import com.easy.wallet.send.overview.TransactionOverviewRoute
import com.easy.wallet.send.pending.PendingRoute
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

internal const val SEND_AMOUNT_ROUTE = "send_amount_route"
internal const val SEND_OVERVIEW_ROUTE = "send_overview_route"
internal const val SEND_PENDING_ROUTE = "send_pending_route"

@Serializable
internal data class SendEntryPointRoute(
  val coinId: String
)

@Serializable
internal data object EnterSendDestinationRoute

@Serializable
internal data object EnterSendAmountRoute

@Serializable
internal data object SendOverviewRoute

@Serializable
internal data object SendCompletedRoute

fun NavController.startSendFlow(coinId: String, navOptions: NavOptions? = null) {
  navigate(SendEntryPointRoute(coinId = coinId), navOptions)
}

fun NavGraphBuilder.attachSendFlowGraph(navController: NavController, onShowSnackbar: (String) -> Unit) {
  navigation<SendEntryPointRoute>(
    startDestination = EnterSendDestinationRoute
  ) {
    composable<EnterSendDestinationRoute> {
      val sharedViewModel: SendSharedViewModel =
        it.sharedViewModel(navController = navController)
      SendDestinationRoute(
        viewModel = sharedViewModel,
        navigateTo = { navController.navigate(EnterSendAmountRoute) },
        popBack = navController::popBackStack
      )
    }

    composable<EnterSendAmountRoute> {
      val sharedViewModel: SendSharedViewModel =
        it.sharedViewModel(navController = navController)
      SendAmountRoute(
        viewModel = sharedViewModel,
        navigateTo = { navController.navigate(SendOverviewRoute) },
        popBack = navController::popBackStack
      )
    }

    composable<SendOverviewRoute> {
      val sharedViewModel: SendSharedViewModel =
        it.sharedViewModel(navController = navController)
      TransactionOverviewRoute(
        viewModel = sharedViewModel,
        showErrorMessage = onShowSnackbar,
        navigateTo = { navController.navigate(SendCompletedRoute) },
        popBack = navController::popBackStack
      )
    }

    composable<SendCompletedRoute> {
      val sharedViewModel: SendSharedViewModel =
        it.sharedViewModel(navController = navController)
      PendingRoute(viewModel = sharedViewModel) {
        navController.popBackStack(EnterSendDestinationRoute, true)
      }
    }
  }
}

@Composable
internal inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
  val navGraphRoute = destination.parent?.route ?: return koinViewModel()
  val parentEntry = remember(key1 = this) {
    navController.getBackStackEntry(navGraphRoute)
  }
  val args = parentEntry.toRoute<SendEntryPointRoute>()
  return koinViewModel(viewModelStoreOwner = parentEntry) { parametersOf(args.coinId) }
}
