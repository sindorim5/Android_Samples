package com.example.googlemapwithweather

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationClass: Application() {
    val WEATHER_URL = "https://api.openweathermap.org/data/2.5/"

    companion object {
        lateinit var mRetrofit : Retrofit
    }

    override fun onCreate() {
        super.onCreate()

        mRetrofit = Retrofit.Builder()
            .baseUrl(WEATHER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}