package com.example.googlemapwithweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.googlemapwithweather.databinding.ActivityMainBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var binding: ActivityMainBinding
    private var iceLandList: ArrayList<LatLng> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        makeIcelandList()

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_view) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        for (i in 0 until iceLandList.size) {
            googleMap.addMarker(
                MarkerOptions()
                    .position(iceLandList[i])
            )
        }
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        TODO("Not yet implemented")
    }


    private fun makeIcelandList() {
        val reykjavik = LatLng(64.133, -21.933)
        val gullfoss = LatLng(64.327751, -20.121331)
        val akureyri = LatLng(65.68389, -18.11056)
        val jokulsarlon = LatLng(64.068833058, -16.206999172)
        val vik = LatLng(63.418632, -19.006048)
        val diamondBeach = LatLng(64.043065, -16.175841)

        iceLandList.add(reykjavik)
        iceLandList.add(gullfoss)
        iceLandList.add(akureyri)
        iceLandList.add(jokulsarlon)
        iceLandList.add(vik)
        iceLandList.add(diamondBeach)
    }
}