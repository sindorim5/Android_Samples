package com.ssafy.example

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class CustomWeatherView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    lateinit var iv: ImageView
    lateinit var tv: TextView

    private val sunny = 0
    private val cloud = 1
    private val rain = 2
    private val snow = 3

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.weather_custom_view, this, false)
        addView(view)
        iv = findViewById(R.id.weather_iv)
        tv = findViewById(R.id.weather_tv)
    }

    fun setWeatherIcon(index: Int) {
        when (index % 4) {
            sunny -> {
                iv.setBackgroundResource(R.drawable.sunny)
                tv.setText(R.string.sunny)
            }
            cloud -> {
                iv.setBackgroundResource(R.drawable.cloud)
                tv.setText(R.string.cloud)
            }
            rain -> {
                iv.setBackgroundResource(R.drawable.rain)
                tv.setText(R.string.rain)
            }
            snow -> {
                iv.setBackgroundResource(R.drawable.snow)
                tv.setText(R.string.snow)
            }
        }
    }

}