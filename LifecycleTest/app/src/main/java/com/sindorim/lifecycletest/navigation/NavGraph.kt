package com.sindorim.lifecycletest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sindorim.lifecycletest.screens.MainScreen
import com.sindorim.lifecycletest.screens.SecondScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Main.route
    ) {
        composable(Screens.Main.route) {
            MainScreen(navController = navController)
        }

        composable(Screens.Second.route) {
            SecondScreen(navController = navController)
        }
    }
}