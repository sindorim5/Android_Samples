package com.sindorim.jetweatherforecast.network

import com.sindorim.jetweatherforecast.BuildConfig
import com.sindorim.jetweatherforecast.model.Weather
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {

    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = BuildConfig.WEATHER_API_KEY
    ) : Weather

}