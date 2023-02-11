package com.ssafy.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var index = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.weatherBtn.setOnClickListener {
            index++
            binding.weatherView.setWeatherIcon(index)
        }
    }
}