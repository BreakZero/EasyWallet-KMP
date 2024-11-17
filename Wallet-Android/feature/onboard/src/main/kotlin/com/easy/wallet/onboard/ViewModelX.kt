package com.easy.wallet.onboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@Composable
internal inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
  val navGraphRoute = destination.parent?.route ?: return koinViewModel()
  val parentEntry = remember(key1 = this) {
    navController.getBackStackEntry(navGraphRoute)
  }
  return koinViewModel(viewModelStoreOwner = parentEntry)
}
