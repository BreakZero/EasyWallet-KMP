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
import androidx.navigation.NavType
import androidx.navigation.compose.navigation
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.easy.wallet.send.SendSharedViewModel
import com.easy.wallet.send.amount.SendAmountRoute
import com.easy.wallet.send.destination.SendDestinationRoute
import com.easy.wallet.send.overview.TransactionOverviewRoute
import org.koin.androidx.compose.koinViewModel
import java.net.URLDecoder
import java.net.URLEncoder
import kotlin.text.Charsets.UTF_8

internal const val sendEnterRoute = "send_enter_route"

internal const val sendAmountRoute = "send_amount_route"
internal const val sendDestinationRoute = "send_destination_route"
internal const val sendOverviewRoute = "send_overview_route"

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

fun NavController.startSendFlow(tokenId: String, navOptions: NavOptions? = null) {
    val encodeTokenId = URLEncoder.encode(tokenId, UTF_8.name())
    navigate("$sendEnterRoute/$encodeTokenId", navOptions)
}

fun NavGraphBuilder.attachSendGraph(navController: NavController) {
    navigation(
        route = "$sendEnterRoute/{$TOKEN_ID_ARG}",
        startDestination = sendDestinationRoute,
        arguments = listOf(
            navArgument(TOKEN_ID_ARG) { type = NavType.StringType }
        )
    ) {
        composable(sendDestinationRoute) {
            val sharedViewModel: SendSharedViewModel =
                it.sharedViewModel(navController = navController)
            SendDestinationRoute(viewModel = sharedViewModel) {
                navController.navigate(sendAmountRoute)
            }
        }

        composable(sendAmountRoute) {
            val sharedViewModel: SendSharedViewModel =
                it.sharedViewModel(navController = navController)
            SendAmountRoute(viewModel = sharedViewModel) {
                navController.navigate(sendOverviewRoute)
            }
        }

        composable(sendOverviewRoute) {
            val sharedViewModel: SendSharedViewModel =
                it.sharedViewModel(navController = navController)
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
