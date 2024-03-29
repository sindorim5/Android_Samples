package com.sindorim.composetest.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.sindorim.composetest.BottomSheetScreen3
import com.sindorim.composetest.MainScreen
import com.sindorim.composetest.ModalBottomSheetScreen
import com.sindorim.composetest.ModalBottomSheetScreen3
import com.sindorim.composetest.screens.autocomplete.AutoCompleteScreen
import com.sindorim.composetest.screens.search.SearchScreen
import com.sindorim.composetest.screens.bottomsheets.BottomSheetScreen
import com.sindorim.composetest.screens.flowexample.FlowExampleScreen
import com.sindorim.composetest.screens.flownetwork.FlowNetworkScreen
import com.sindorim.composetest.screens.flownetwork.FlowNetworkViewModel
import com.sindorim.composetest.screens.gallery.GalleryScreen
import com.sindorim.composetest.screens.stt.SpeechScreen

@ExperimentalFoundationApi
@ExperimentalPermissionsApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun MyNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MyScreens.MainScreen.name
    ) {

        composable(MyScreens.MainScreen.name) {
            MainScreen(navController = navController)
        }

        composable(MyScreens.BottomSheetScreen.name) {
            BottomSheetScreen(navController = navController)
        }

        composable(MyScreens.ModalBottomSheetScreen.name) {
            ModalBottomSheetScreen(navController = navController)
        }

        composable(MyScreens.BottomSheetScreen3.name) {
            BottomSheetScreen3(navController = navController)
        }

        composable(MyScreens.ModalBottomSheetScreen3.name) {
            ModalBottomSheetScreen3(navController = navController)
        }

        composable(MyScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }

        composable(MyScreens.SpeechScreen.name) {
            SpeechScreen(navController = navController)
        }

        composable(MyScreens.AutoCompleteScreen.name) {
            AutoCompleteScreen(navController = navController)
        }

        composable(MyScreens.GalleryScreen.name) {
            GalleryScreen(navController = navController)
        }

        composable(MyScreens.FlowExampleScreen.name) {
            FlowExampleScreen(navController = navController)
        }

        composable(MyScreens.FlowNetworkScreen.name) {
            val flowNetworkViewModel = hiltViewModel<FlowNetworkViewModel>()
            FlowNetworkScreen(
                navController = navController, viewModel = flowNetworkViewModel
            )
        }

    }

}