package com.sindorim.jetareader.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sindorim.jetareader.screens.SplashScreen
import com.sindorim.jetareader.screens.details.BookDetailScreen
import com.sindorim.jetareader.screens.home.HomeScreenViewModel
import com.sindorim.jetareader.screens.home.ReaderHomeScreen
import com.sindorim.jetareader.screens.login.LoginScreen
import com.sindorim.jetareader.screens.search.BookSearchScreen
import com.sindorim.jetareader.screens.search.BooksSearchViewModel
import com.sindorim.jetareader.screens.stats.ReaderStatsScreen
import com.sindorim.jetareader.screens.update.BookUpdateScreen

@ExperimentalComposeUiApi
@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ReaderScreens.SplashScreen.name
    ) {
        composable(ReaderScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }

        composable(ReaderScreens.LoginScreen.name) {
            LoginScreen(navController = navController)
        }

        composable(ReaderScreens.ReaderStatsScreen.name) {
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            ReaderStatsScreen(navController = navController, viewModel = homeViewModel)
        }

        composable(ReaderScreens.ReaderHomeScreen.name) {
            ReaderHomeScreen(navController = navController)
        }

        composable(ReaderScreens.BookSearchScreen.name) {
            val searchViewModel = hiltViewModel<BooksSearchViewModel>()
            BookSearchScreen(navController = navController, viewModel = searchViewModel)
        }

        val detailName = ReaderScreens.BookDetailScreen.name
        composable(
            route = "$detailName/{bookId}",
            arguments = listOf(
                navArgument("bookId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("bookId").let { argument ->
                BookDetailScreen(navController = navController, bookId = argument.toString())
            }
        }

        val updateName = ReaderScreens.BookUpdateScreen.name
        composable(
            route = "$updateName/{bookId}",
            arguments = listOf(
                navArgument("bookId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("bookId").let { argument ->
                BookUpdateScreen(navController = navController, googleBookId = argument.toString())
            }
        }
    }

}