package com.sindorim.composetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.sindorim.composetest.navigation.MyNavigation
import com.sindorim.composetest.screens.search.SearchScreen
import com.sindorim.composetest.ui.theme.ComposeTestTheme

class MainActivity : ComponentActivity() {

    @ExperimentalPermissionsApi
    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
                ComposeTestApp()
            }
        }
    }
}

@ExperimentalPermissionsApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun ComposeTestApp() {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MyNavigation()
//            val navController = NavController(LocalContext.current)
//            SearchScreen(navController = navController)
        }
    }
}

@Preview(showBackground = true)
@ExperimentalMaterial3Api
@Composable
fun DefaultPreview() {
    ComposeTestTheme {

    }
}

