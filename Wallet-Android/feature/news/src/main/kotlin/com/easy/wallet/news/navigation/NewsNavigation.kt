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
import androidx.navigation.navigation
import com.easy.wallet.news.NewsRoute
import kotlinx.serialization.Serializable

@Serializable
internal data object NewsTabRoute

@Serializable
data object NewsEntryRoute

fun NavController.selectedNewsTab(navOptions: NavOptions? = null) = navigate(NewsTabRoute, navOptions)

fun NavGraphBuilder.attachNewsTabGraph(nestedGraphs: NavGraphBuilder.() -> Unit) {
  navigation<NewsTabRoute>(startDestination = NewsEntryRoute) {
    composable<NewsEntryRoute> {
      NewsRoute()
    }
    nestedGraphs()
  }
}

internal fun launchCustomChromeTab(
  context: Context,
  uri: Uri,
  @ColorInt toolbarColor: Int
) {
  val customTabBarColor = CustomTabColorSchemeParams
    .Builder()
    .setToolbarColor(toolbarColor)
    .build()
  val customTabsIntent = CustomTabsIntent
    .Builder()
    .setDefaultColorSchemeParams(customTabBarColor)
    .build()

  customTabsIntent.launchUrl(context, uri)
}
