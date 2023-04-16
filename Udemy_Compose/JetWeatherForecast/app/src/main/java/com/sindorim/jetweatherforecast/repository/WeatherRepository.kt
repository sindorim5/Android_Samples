package com.sindorim.jetweatherforecast.repository

import android.util.Log
import com.sindorim.jetweatherforecast.data.DataOrException
import com.sindorim.jetweatherforecast.model.Weather
import com.sindorim.jetweatherforecast.model.WeatherObject
import com.sindorim.jetweatherforecast.network.WeatherApi
import javax.inject.Inject

private const val TAG = "WeatherRepository_SDR"
class WeatherRepository @Inject constructor(private val api: WeatherApi) {

    suspend fun getWeather(cityQuery: String): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityQuery)
        } catch (e: Exception) {
            Log.d(TAG, "getWeather: $e")
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }

}