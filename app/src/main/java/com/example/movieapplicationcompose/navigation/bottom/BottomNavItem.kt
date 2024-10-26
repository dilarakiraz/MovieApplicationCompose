package com.example.movieapplicationcompose.navigation.bottom

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.movieapplicationcompose.R

sealed class BottomNavItem(val route: String, val title: String, val icon: BottomNavIcon) {
    object Home : BottomNavItem("Home screen", "Home", BottomNavIcon.ImageVectorIcon(Icons.Rounded.Home))
    object Favorites : BottomNavItem("Favorites screen", "Favorites", BottomNavIcon.ImageVectorIcon(Icons.Rounded.Favorite))
    object Chat : BottomNavItem("Chat screen", "Chat", BottomNavIcon.DrawableIcon(R.drawable.chatbot))
}

sealed class BottomNavIcon {
    data class ImageVectorIcon(val imageVector: ImageVector) : BottomNavIcon()
    data class DrawableIcon(val drawableId: Int) : BottomNavIcon()
}