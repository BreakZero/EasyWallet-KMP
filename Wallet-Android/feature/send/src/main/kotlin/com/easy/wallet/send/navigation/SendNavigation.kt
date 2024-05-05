package com.easy.wallet.send.navigation

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.easy.wallet.send.SendSharedViewModel
import com.easy.wallet.send.amount.SendAmountRoute
import com.easy.wallet.send.destination.SendDestinationRoute
import com.easy.wallet.send.overview.TransactionOverviewRoute
import com.easy.wallet.send.pending.PendingRoute
import org.koin.androidx.compose.koinViewModel
import java.net.URLDecoder
import java.net.URLEncoder
import kotlin.text.Charsets.UTF_8

internal const val sendEnterRoute = "send_enter_route"

internal const val sendAmountRoute = "send_amount_route"
internal const val sendDestinationRoute = "send_destination_route"
internal const val sendOverviewRoute = "send_overview_route"
internal const val sendPendingRoute = "send_pending_route"

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

fun NavController.startSendFlow(coinId: String, platformId: String, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    val encodedCoinId = URLEncoder.encode(coinId, URL_CHARACTER_ENCODING)
    val encodedPlatformId = URLEncoder.encode(platformId, URL_CHARACTER_ENCODING)

    val enterRoute = "$sendEnterRoute/$encodedCoinId/$encodedPlatformId"

    navigate(enterRoute) { navOptions() }
}

fun NavGraphBuilder.attachSendGraph(
    navController: NavController,
    onShowSnackbar: (String) -> Unit
) {
    navigation(
        route = "$sendEnterRoute/{$COIN_ID_ARG}/{$PLATFORM_ID_ARG}",
        startDestination = sendDestinationRoute,
        arguments = listOf(
            navArgument(COIN_ID_ARG) { type = NavType.StringType },
            navArgument(PLATFORM_ID_ARG) { type = NavType.StringType }
        )
    ) {
        composable(sendDestinationRoute) {
            val sharedViewModel: SendSharedViewModel =
                it.sharedViewModel(navController = navController)
            SendDestinationRoute(
                viewModel = sharedViewModel,
                navigateTo = navController::navigate,
                popBack = navController::popBackStack
            )
        }

        composable(sendAmountRoute) {
            val sharedViewModel: SendSharedViewModel =
                it.sharedViewModel(navController = navController)
            SendAmountRoute(
                viewModel = sharedViewModel,
                navigateTo = navController::navigate,
                popBack = navController::popBackStack
            )
        }

        composable(sendOverviewRoute) {
            val sharedViewModel: SendSharedViewModel =
                it.sharedViewModel(navController = navController)
            TransactionOverviewRoute(
                viewModel = sharedViewModel,
                showErrorMessage = onShowSnackbar,
                navigateTo = navController::navigate,
                popBack = navController::popBackStack
            )
        }

        composable(sendPendingRoute) {
            val sharedViewModel: SendSharedViewModel =
                it.sharedViewModel(navController = navController)
            PendingRoute(viewModel = sharedViewModel) {
                navController.popBackStack(sendDestinationRoute, true)
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
    return koinViewModel(viewModelStoreOwner = parentEntry)
}
