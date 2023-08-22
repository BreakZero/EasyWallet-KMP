package com.easy.wallet.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

 enum class TopLevelDestination(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean = false,
    val badgeCount: Int? = null
) {
     HOME(
         title = "Home",
         selectedIcon = Icons.Default.Home,
         unselectedIcon = Icons.Default.Home
     ),
     MARKETPLACE(
         title = "Marketplace",
         selectedIcon = Icons.Default.Home,
         unselectedIcon = Icons.Default.Home
     ),
     DISCOVER(
         title = "Discover",
         selectedIcon = Icons.Default.Home,
         unselectedIcon = Icons.Default.Home
     )
}