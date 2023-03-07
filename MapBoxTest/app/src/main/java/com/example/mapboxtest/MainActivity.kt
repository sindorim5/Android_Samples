package com.example.mapboxtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mapboxtest.databinding.ActivityMainBinding
import com.mapbox.maps.MapView
import com.mapbox.maps.Style


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mapView = binding.mapView.apply {
            getMapboxMap().loadStyleUri(Style.DARK)
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }


}