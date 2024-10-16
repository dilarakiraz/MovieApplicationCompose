package com.example.movieapplicationcompose.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapplicationcompose.navigation.bottom.BottomNavBar
import com.example.movieapplicationcompose.ui.screens.BannerScreen
import com.example.movieapplicationcompose.ui.screens.DetailsScreen
import com.example.movieapplicationcompose.ui.screens.FavoritesScreen
import com.example.movieapplicationcompose.ui.screens.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val bannerScreen = "Banner screen"
    val detailsScreen = "Details screen/{id}"
    val homeScreen = "Home screen"
    val favoritesScreen = "Favorites screen"

    val bottomNavVisible = currentRoute != "Banner screen" && currentRoute != "Details screen/{id}"

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = bannerScreen,
            Modifier.fillMaxSize()
        ) {
            composable(bannerScreen) {
                BannerScreen(navController = navController)
            }
            composable(homeScreen) {
                HomeScreen(navController = navController)
            }
            composable(favoritesScreen) {
                FavoritesScreen(navController = navController)
            }
            composable(
                detailsScreen,
                arguments = listOf(
                    navArgument(
                        name = "id"
                    ) {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id")
                id?.let {
                    DetailsScreen(navController = navController, id = it)
                }
            }
        }
        if (bottomNavVisible) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .height(100.dp)
            ) {
                BottomNavBar(navController)
            }
        }
    }
}
