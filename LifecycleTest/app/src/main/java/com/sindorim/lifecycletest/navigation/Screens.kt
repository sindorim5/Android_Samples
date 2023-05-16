package com.sindorim.lifecycletest.navigation

sealed class Screens(val route: String) {

    object Main: Screens(route = "main_screen")

    object Second: Screens(route = "second_screen")

}