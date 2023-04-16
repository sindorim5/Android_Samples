package com.sindorim.jetweatherforecast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sindorim.jetweatherforecast.navigation.WeatherNavigation
import com.sindorim.jetweatherforecast.ui.theme.JetWeatherForecastTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherApp()
        } // End of setContent
    } // End of onCreate
} // End of MainActivity

@Composable
fun WeatherApp() {
    JetWeatherForecastTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherNavigation()
            }
        } // End of Surface
    } // End of JetWeatherForecastTheme
} // End of WeatherApp


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherApp()
}