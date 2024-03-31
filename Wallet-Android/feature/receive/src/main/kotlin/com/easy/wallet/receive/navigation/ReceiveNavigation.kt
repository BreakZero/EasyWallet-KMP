package com.easy.wallet.receive.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.easy.wallet.receive.ReceiveRoute

const val Receive_Route = "receive_route"

fun NavController.navigateToReceive(navOptions: NavOptions? = null) {
    this.navigate(Receive_Route, navOptions)
}

fun NavGraphBuilder.attachReceiveModule() {
    composable(route = Receive_Route) {
        ReceiveRoute()
    }
}
