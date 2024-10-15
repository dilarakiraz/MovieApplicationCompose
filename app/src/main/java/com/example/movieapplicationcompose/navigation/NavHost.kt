package com.example.movieapplicationcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Banner screen") {
        composable("Banner screen") {
            BannerScreen(navController = navController)
        }
        composable("Home screen") {
            HomeScreen(navController = navController)
        }
        composable(
            "Details screen/{id}",
            arguments = listOf(
                navArgument(
                    name = "id"
                ) {
                    type = NavType.IntType
                }
            )
        ) { id ->
            id.arguments?.getInt("id")?.let { id1 ->
                DetailsScreen(id = id1)
            }
        }
    }
}