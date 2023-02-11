package com.example.googlemaptes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val multicampus = LatLng(37.5013068, 127.0385654)
        val tokyoTower = LatLng(35.65879841066742, 139.74540071134552)
        val greatWall = LatLng(40.4320628434537, 116.57039635565916)
        val eiffelTower = LatLng(48.858532364084724, 2.2944490988322266)
        val statueOfLiberty = LatLng(41.14606038875511, -74.12856842459752)

        googleMap.addMarker(
            MarkerOptions()
                .position(multicampus)
                .title("역삼 멀티캠퍼스")
        )

        googleMap.addMarker(
            MarkerOptions()
                .position(tokyoTower)
                .title("도쿄 타워")
        )

        googleMap.addMarker(
            MarkerOptions()
                .position(greatWall)
                .title("만리장성")
        )

        googleMap.addMarker(
            MarkerOptions()
                .position(eiffelTower)
                .title("에펠탑")
        )

        googleMap.addMarker(
            MarkerOptions()
                .position(statueOfLiberty)
                .title("자유의 여신상")
        )

        googleMap.setOnMarkerClickListener(this)

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(multicampus, 15F))
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        Toast.makeText(this, marker.title, Toast.LENGTH_SHORT).show()
        return true
    }
}