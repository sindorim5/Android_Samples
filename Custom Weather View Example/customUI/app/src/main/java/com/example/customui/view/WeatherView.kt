package com.example.customui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import com.example.customui.R

class WeatherView constructor(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    val weather_img : AppCompatImageView
    val weather_txt : AppCompatTextView

    init {
        val inflater: LayoutInflater = getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.weatherview, this, false)
        addView(view)

        weather_img = view.findViewById<AppCompatImageView>(R.id.weather_img)
        weather_txt = view.findViewById<AppCompatTextView>(R.id.weather_text)
    }

    fun setWeather(weatherNumber: Number) {
        if (weatherNumber == 0) {
            weather_img.setBackgroundResource(R.drawable.sunny)
            weather_txt.setText(R.string.sunny)
        } else if (weatherNumber == 1) {
            weather_img.setBackgroundResource(R.drawable.cloud)
            weather_txt.setText(R.string.cloud)
        } else if (weatherNumber == 2) {
            weather_img.setBackgroundResource(R.drawable.rain)
            weather_txt.setText(R.string.rain)
        } else if (weatherNumber == 3) {
            weather_img.setBackgroundResource(R.drawable.snow)
            weather_txt.setText(R.string.snow)
        } else {
            return
        }
    }
}