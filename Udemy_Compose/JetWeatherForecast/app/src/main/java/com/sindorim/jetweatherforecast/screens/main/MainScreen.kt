package com.sindorim.jetweatherforecast.screens.main

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sindorim.jetweatherforecast.data.DataOrException
import com.sindorim.jetweatherforecast.model.Weather
import com.sindorim.jetweatherforecast.widgets.WeatherAppBar


@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData(city = "Seattle")

    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weather = weatherData.data!!, navController)
    }


} // End of MainScreen

@Composable
fun MainScaffold(weather: Weather, navController: NavController) {

    Scaffold(topBar = {
        WeatherAppBar(title = "Helena, MT")
    }) {
        MainContent(data = weather)
    }


} // End of MainScaffold

@Composable
fun MainContent(data: Weather) {

}