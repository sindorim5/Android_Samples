package com.sindorim.paging3tutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.sindorim.paging3tutorial.navigation.SetupNavGraph
import com.sindorim.paging3tutorial.ui.theme.Paging3TutorialTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@ExperimentalMaterial3Api
@ExperimentalCoilApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Paging3TutorialTheme {
                val navHostController = rememberNavController()
                SetupNavGraph(navHostController = navHostController)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Paging3TutorialTheme {
        Greeting("Android")
    }
}