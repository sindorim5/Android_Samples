package com.example.customui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.example.customui.view.WeatherView

class MainActivity : AppCompatActivity() {
    var index = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weatherView = findViewById<WeatherView>(R.id.my_weather_view)
        val weather_change = findViewById<AppCompatButton>(R.id.weather_change)

        weather_change.setOnClickListener {
            weatherView.setWeather(index++)

            if (index >= 4)
                index = 0
        }
    }
}