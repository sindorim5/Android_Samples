package com.sindorim.jetweatherforecast.utils

import com.sindorim.jetweatherforecast.model.FeelsLike
import com.sindorim.jetweatherforecast.model.Temp
import com.sindorim.jetweatherforecast.model.WeatherItem
import com.sindorim.jetweatherforecast.model.WeatherObject

object Constants {
    const val BASE_URL = "https://api.openweathermap.org/"

    var dummy_weather_item: WeatherItem = WeatherItem(
        dt = 1681732800,
        sunrise = 1681711069,
        sunset = 1681758847,
        temp = Temp(
            day = 24.96,
            min = 16.22,
            max = 27.6,
            night = 19.8,
            eve = 25.66,
            morn = 16.46
        ),
        feels_like = FeelsLike(
            day = 24.35,
            night = 18.96,
            eve = 25.14,
            morn = 15.62
        ),
        pressure = 1012,
        humidity = 32,
        weather = listOf(
            WeatherObject(
                id = 802,
                main = "Clouds",
                description = "scattered clouds",
                icon = "03d"
            )
        ),
        speed = 4.68,
        deg = 5,
        gust = 8.26,
        clouds = 26,
        pop = 0.0,
        rain = 0.0
    )


}