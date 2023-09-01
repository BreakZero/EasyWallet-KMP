package com.easy.wallet.news.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.easy.wallet.news.NewsRoute

const val newsTabRoute = "_news_tab_route"
internal const val newsRoute = "_news_route"

fun NavController.selectedNewsTab(navOptions: NavOptions? = null) {
    this.navigate(newsTabRoute, navOptions)
}

fun NavGraphBuilder.newsTabScreen() {
    composable(route = newsTabRoute) {
        NewsRoute()
    }
}