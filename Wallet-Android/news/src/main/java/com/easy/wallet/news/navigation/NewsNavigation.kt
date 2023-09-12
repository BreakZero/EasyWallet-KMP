package com.easy.wallet.news.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.easy.wallet.news.NewsDetailScreen
import com.easy.wallet.news.NewsRoute

const val newsTabRoute = "_news_tab_route"
internal const val newsDetailRoute = "_news_detail_route"

fun NavController.selectedNewsTab(navOptions: NavOptions? = null) {
    this.navigate(newsTabRoute, navOptions)
}

fun NavGraphBuilder.newsGraph(navController: NavController) {
    composable(route = newsTabRoute) {
        NewsRoute {
            val encodedUrl = Uri.encode(it)
            navController.navigate("$newsDetailRoute/$encodedUrl")
        }
    }
    composable(
        route = "$newsDetailRoute/{url}",
        arguments = listOf(
            navArgument("url") { type = NavType.StringType },
        ),
    ) { backStackEntry ->
        val url = backStackEntry.arguments?.getString("url").orEmpty()
        NewsDetailScreen(url = Uri.decode(url), popBack = navController::popBackStack)
    }
}