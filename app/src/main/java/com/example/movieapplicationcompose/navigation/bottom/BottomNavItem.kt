package com.example.movieapplicationcompose.navigation.bottom

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Send
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var route: String, var title: String, var icon: ImageVector) {
    object Home : BottomNavItem("Home screen", "Home", Icons.Rounded.Home)
    object Favorites : BottomNavItem("Favorites screen", "Favorites", Icons.Rounded.Favorite)
    object Chat : BottomNavItem("Chat screen", "Chat", Icons.Rounded.Send)
}