package com.easy.wallet.news.navigation

import android.content.Context
import android.net.Uri
import androidx.annotation.ColorInt
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.easy.wallet.news.NewsRoute

const val newsTabRoute = "_news_tab_route"

fun NavController.selectedNewsTab(navOptions: NavOptions? = null) {
    this.navigate(newsTabRoute, navOptions)
}

fun NavGraphBuilder.attachNewsGraph() {
    composable(route = newsTabRoute) {
        NewsRoute()
    }
}

internal fun launchCustomChromeTab(context: Context, uri: Uri, @ColorInt toolbarColor: Int) {
    val customTabBarColor = CustomTabColorSchemeParams.Builder()
        .setToolbarColor(toolbarColor).build()
    val customTabsIntent = CustomTabsIntent.Builder()
        .setDefaultColorSchemeParams(customTabBarColor)
        .build()

    customTabsIntent.launchUrl(context, uri)
}
