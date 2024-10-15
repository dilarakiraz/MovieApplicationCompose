package com.example.movieapplicationcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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
    }
}