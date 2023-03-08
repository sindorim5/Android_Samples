package com.example.googlemapwithweather

import android.annotation.SuppressLint
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.googlemapwithweather.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainActivity_sdr"

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMainBinding
    private var iceLandList: ArrayList<LatLng> = arrayListOf()

    private var googleMap: GoogleMap? = null
    private var currentMarker: Marker? = null
    private lateinit var currentPosition: LatLng


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        makeIcelandList()

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_view) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        for (i in 0 until iceLandList.size) {
            googleMap.addMarker(
                MarkerOptions()
                    .position(iceLandList[i])
            )
        }

        setDefaultLocation()
        
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

    private fun setDefaultLocation() {
        val defaultLocation = Location("").apply {
            latitude = 64.9830
            longitude = -18.6168
        }

        setCurrentLocation(defaultLocation, "default", "default_location")
    }

    private fun setCurrentLocation(
        location: Location,
        markerTitle: String?,
        markerSnippet: String?
    ) {
        currentMarker?.remove()

        val currentLatLng = LatLng(location.latitude, location.longitude)

        val markerOptions = MarkerOptions()
        markerOptions.position(currentLatLng)
        markerOptions.title(markerTitle)
        markerOptions.snippet(markerSnippet)
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

        currentMarker = this.googleMap?.addMarker(markerOptions)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLatLng, 6f)
        this.googleMap?.animateCamera(cameraUpdate)
    }

    private fun getWeatherData(lat: Double, lon: Double) {
        val appid = BuildConfig.WEATHER_API_KEY
        val weatherInterface = ApplicationClass.mRetrofit.create(WeatherInterface::class.java)
        weatherInterface.getCurrentWeather(lat, lon, appid).enqueue(object :
            Callback<WeatherResponse> {

            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.code() == 200) {
                    val res = response.body() as WeatherResponse
                    Toast.makeText(this@MainActivity, "${res.main.temp}", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d(TAG, "getWeatherData - onResponse : Error code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.d(TAG, t.message ?: "통신오류")
            }
        })
    }

}