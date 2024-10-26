package com.example.movieapplicationcompose.navigation.bottom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(navController: NavHostController) {

    val topLevelRoutes = listOf(
        BottomNavItem.Home,
        BottomNavItem.Favorites,
        BottomNavItem.Chat
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomAppBar(containerColor = Color.White.copy(alpha = 0.8f)) {
        topLevelRoutes.forEach { item ->
            val isSelected = currentDestination?.route == item.route
            IconButton(
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    when (item.icon) {
                        is BottomNavIcon.ImageVectorIcon -> {
                            Icon(
                                imageVector = item.icon.imageVector,
                                contentDescription = item.title,
                                tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
                            )
                        }
                        is BottomNavIcon.DrawableIcon -> {
                            Icon(
                                painter = painterResource(id = item.icon.drawableId),
                                contentDescription = item.title,
                                tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
                            )
                        }
                    }
                    Text(
                        text = item.title,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
