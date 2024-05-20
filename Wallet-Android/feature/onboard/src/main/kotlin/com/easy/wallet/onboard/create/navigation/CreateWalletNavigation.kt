package com.easy.wallet.onboard.create.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.easy.wallet.android.core.extensions.ObserveAsEvents
import com.easy.wallet.onboard.create.CreateWalletEvent
import com.easy.wallet.onboard.create.CreateWalletViewModel
import com.easy.wallet.onboard.create.password.CreatePasswordRoute
import com.easy.wallet.onboard.create.secure.SecureRoute
import com.easy.wallet.onboard.create.seed.SeedPhraseRoute
import com.easy.wallet.onboard.sharedViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data object WalletEntryPointRoute

@Serializable
internal data object CreateWalletRoute

@Serializable
internal data object CheckSecureRoute
@Serializable
internal data object MnemonicPhrasesOverviewRoute

fun NavController.toCreateWallet(navOptions: NavOptions? = null) {
    this.navigate(CreateWalletRoute, navOptions)
}

internal fun NavController.toSecure(navOptions: NavOptions? = null) {
    this.navigate(CheckSecureRoute, navOptions)
}
internal fun NavController.toViewMnemonicPhrases(navOptions: NavOptions? = null) {
    this.navigate(MnemonicPhrasesOverviewRoute, navOptions)
}

fun NavGraphBuilder.attachCreateWalletGraph(navController: NavController) {
    navigation<WalletEntryPointRoute>(startDestination = CreateWalletRoute) {
        composable<CreateWalletRoute> {
            val viewModel: CreateWalletViewModel = it.sharedViewModel(navController = navController)
            ObserveAsEvents(flow = viewModel.navigationEvents) {
                when(it) {
                    CreateWalletEvent.PopBack -> navController.popBackStack()
                    CreateWalletEvent.NavigateToSecure -> { navController.toSecure() }
                    else -> Unit
                }
            }
            CreatePasswordRoute(viewModel = viewModel)
        }
        composable<CheckSecureRoute> {
            val viewModel: CreateWalletViewModel = it.sharedViewModel(navController = navController)
            ObserveAsEvents(flow = viewModel.navigationEvents) {
                when(it) {
                    CreateWalletEvent.PopBack -> navController.popBackStack()
                    CreateWalletEvent.NavigateToMnemonic -> { navController.toViewMnemonicPhrases() }
                    else -> Unit
                }
            }
            SecureRoute(viewModel = viewModel)
        }

        composable<MnemonicPhrasesOverviewRoute> {
            val viewModel: CreateWalletViewModel = it.sharedViewModel(navController = navController)
            ObserveAsEvents(flow = viewModel.navigationEvents) {
                when (it) {
                    is CreateWalletEvent.OnCreateWallet -> navController.popBackStack(WalletEntryPointRoute, true)
                    CreateWalletEvent.PopBack -> navController.popBackStack()
                    else -> Unit
                }
            }
            SeedPhraseRoute(viewModel = viewModel)
        }
    }
}
