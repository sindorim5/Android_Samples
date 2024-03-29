package com.sindorim.jetareader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sindorim.jetareader.navigation.ReaderNavigation
import com.sindorim.jetareader.ui.theme.JetAReaderTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetAReaderTheme {
                ReaderApp()
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun ReaderApp() {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            ReaderNavigation()
        }


    }
}

@Preview(showBackground = true)
@ExperimentalComposeUiApi
@Composable
fun DefaultPreview() {
    JetAReaderTheme {
        ReaderApp()
    }
}