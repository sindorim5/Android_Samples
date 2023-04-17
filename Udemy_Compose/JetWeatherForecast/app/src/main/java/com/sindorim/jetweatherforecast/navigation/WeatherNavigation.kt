package com.sindorim.jetweatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sindorim.jetweatherforecast.screens.main.MainScreen
import com.sindorim.jetweatherforecast.screens.WeatherSplashScreen
import com.sindorim.jetweatherforecast.screens.main.MainViewModel
import com.sindorim.jetweatherforecast.screens.search.SearchScreen

@ExperimentalComposeUiApi
@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WeatherScreens.SplashScreen.name
    ) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        // path variable
        val route = WeatherScreens.MainScreen.name
        composable(
            route = "$route/{city}",
            arguments = listOf(navArgument(name = "city") {
                type = NavType.StringType
            })
        ) { navBack ->
            navBack.arguments?.getString("city").let { city ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController, mainViewModel, city = city)
            }
        }

        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }
    }
}