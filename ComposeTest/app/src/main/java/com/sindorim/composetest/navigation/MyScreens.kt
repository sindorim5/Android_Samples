package com.sindorim.composetest.navigation

enum class MyScreens {
    MainScreen,
    BottomSheetScreen,
    ModalBottomSheetScreen,
    BottomSheetScreen3,
    ModalBottomSheetScreen3,
    SearchScreen;

    companion object  {
        fun fromRoute(route: String?): MyScreens
                = when(route?.substringBefore("/")) {
            MainScreen.name -> MainScreen
            BottomSheetScreen.name -> BottomSheetScreen
            ModalBottomSheetScreen.name -> ModalBottomSheetScreen
            BottomSheetScreen3.name -> BottomSheetScreen3
            ModalBottomSheetScreen3.name -> ModalBottomSheetScreen3
            SearchScreen.name -> SearchScreen
            null -> MainScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }

}