package com.sindorim.paging3tutorial.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.sindorim.paging3tutorial.screens.home.HomeScreen
import com.sindorim.paging3tutorial.screens.search.SearchScreen


@ExperimentalCoilApi
@ExperimentalPagingApi
@ExperimentalMaterial3Api
@Composable
fun SetupNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route){
            HomeScreen(navHostController = navHostController)
        }
        composable(route = Screen.Search.route){
            SearchScreen(navHostController = navHostController)
        }
    }
}