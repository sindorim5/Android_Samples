package com.example.googlemapwithweather

import android.annotation.SuppressLint
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.googlemapwithweather.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.collections.MarkerManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.MalformedURLException
import java.net.URL
import kotlin.math.round


private const val TAG = "MainActivity_sdr"

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMainBinding
    private var iceLandList: ArrayList<IcelandItem> = arrayListOf()

    private var googleMap: GoogleMap? = null
    private var currentMarker: Marker? = null
    private lateinit var currentPosition: LatLng
    private lateinit var clusterManager: ClusterManager<IcelandItem>
    private lateinit var normalMarkerCollection: MarkerManager.Collection
    private lateinit var mCloudTileOverlay: TileOverlay


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        makeIcelandList()

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_view) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    @SuppressLint("PotentialBehaviorOverride")
    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap

        // custom style map
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = googleMap!!.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this, R.raw.dark_style_json
                )
            )
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }

        // setDefaultLocation
        setDefaultLocation()

        // cluster, marker click
        clusterManager = ClusterManager<IcelandItem>(this, googleMap)
        gMap.setOnMarkerClickListener(clusterManager.markerManager)
        normalMarkerCollection = clusterManager.markerManager.newCollection()
        for (i in 0 until iceLandList.size) {
            clusterManager.addItem(iceLandList[i])
            normalMarkerCollection.apply {
                addMarker(
                    MarkerOptions()
                        .position(iceLandList[i].position)
                        .title(iceLandList[i].title)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                )
            }
        }

//        normalMarkerCollection.setOnMarkerClickListener {
//            GoogleMap.OnMarkerClickListener { marker ->
//                Log.d(TAG, "onMapReady: ${marker.title}")
//                Toast.makeText(
//                    this,
//                    "lat:${marker.position.latitude}\nlng:${marker.position.longitude}",
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            false
//        }

        clusterManager.setOnClusterItemClickListener {
            Log.d(TAG, "onMapReady: click")
            Toast.makeText(
                this,
                "lat:${it.position.latitude}\nlng:${it.position.longitude}",
                Toast.LENGTH_SHORT
            ).show()
            false
        }

        gMap.setOnMapLongClickListener {
            currentPosition = it

            val location = Location("").apply {
                this.latitude = it.latitude
                this.longitude = it.longitude
            }

            setCurrentLocation(location, "current", "curr")
            getWeatherData(it.latitude, it.longitude)
        }

        val tileProvider: TileProvider = object : UrlTileProvider(256, 256) {
            override fun getTileUrl(x: Int, y: Int, z: Int): URL? {

                val s =
                    "https://tile.openweathermap.org/map/clouds_new/${z}/${x}/${y}.png?appid=${BuildConfig.WEATHER_API_KEY}"

                val tileUrl: URL? = try {
                    URL(s)
                } catch (e: MalformedURLException) {
                    throw AssertionError(e)
                }
                Log.d(TAG, "getTileUrl: $tileUrl")
                return tileUrl
            }

//            private fun checkTileExists(x: Int, y: Int, zoom: Int): Boolean {
//                val minZoom = 6
//                val maxZoom = 18
//                Log.d(TAG, "zoom: $zoom")
//                Log.d(TAG, "checkTileExists: ${zoom in minZoom..maxZoom}")
//                return zoom in minZoom..maxZoom
//            }
        }
//        mCloudTileOverlay = gMap.addTileOverlay(
//            TileOverlayOptions()
//                .tileProvider(tileProvider)
//                .transparency(0.9f)
//        )!!
    }

    private fun makeIcelandList() {
        val reykjavik = IcelandItem(64.133, -21.933, "reykjavik", "reykjavik_snipp")
        val gullfoss = IcelandItem(64.327751, -20.121331, "gullfoss", "gullfoss_snipp")
        val akureyri = IcelandItem(65.68389, -18.11056, "akureyri", "akureyri_snipp")
        val jokulsarlon =
            IcelandItem(64.068833058, -16.206999172, "jokulsarlon", "jokulsarlon_snipp")
        val vik = IcelandItem(63.418632, -19.006048, "vik", "vik_snipp")
        val diamondBeach = IcelandItem(64.043065, -16.175841, "Diamond Beach", "D-Beach_snipp")

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

        val markerOptions = MarkerOptions().apply {
            position(currentLatLng)
            title(markerTitle)
            snippet(markerSnippet)
            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        }

        currentPosition = currentLatLng
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

                    Log.d(TAG, "onResponse: $res")

                    Toast.makeText(
                        this@MainActivity,
                        "name:${res.name}\nTemp:${round((res.main.temp - 273.15) * 10 / 10)}\nCloud:${res.clouds.all}",
                        Toast.LENGTH_SHORT
                    ).show()
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