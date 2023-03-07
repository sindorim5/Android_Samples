package com.example.mapboxtest

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import com.example.mapboxtest.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mapView: MapView
    private lateinit var annotationApi: AnnotationPlugin
    private lateinit var annotationConfig: AnnotationConfig
    private lateinit var pointAnnotationManager : PointAnnotationManager
    var layerIDD = "map_annotation"

    var markerList : ArrayList<PointAnnotationOptions> = ArrayList()
    var latitudeList : ArrayList<Double> = ArrayList()
    var longitudeList : ArrayList<Double> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createLatLongForMarker()

        mapView = binding.mapView.apply {
            getMapboxMap().loadStyleUri(Style.DARK,
                object : Style.OnStyleLoaded{
                    override fun onStyleLoaded(style: Style) {
                        zoomCamera()

                        // add Marker
                        annotationApi = mapView.annotations
                        annotationConfig = AnnotationConfig(
                            layerId = layerIDD
                        )

                        // initialize point annotation manager
                        pointAnnotationManager = annotationApi.createPointAnnotationManager(annotationConfig)

                        createMarkerOnMap()
                    }
                })
        }
        mapView.setOnClickListener {
            Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show()
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

    private fun zoomCamera() {
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder().center(Point.fromLngLat(
                75.8577, 22.7196
            ))
                .zoom(11.0)
                .build()
        )
    }

    private fun createLatLongForMarker() {
        latitudeList.add(22.7196)
        longitudeList.add(75.8577)

        latitudeList.add(23.1765)
        longitudeList.add(75.7885)

        latitudeList.add(22.9676)
        longitudeList.add(76.8534)
    }

    private fun clearAnnotation() {
        markerList = ArrayList()
        pointAnnotationManager.deleteAll()
    }


    // Create Marker
    private fun createMarkerOnMap() {
        clearAnnotation()

        // Adding Click Event of Marker
        pointAnnotationManager.addClickListener(OnPointAnnotationClickListener { annotation ->
            onMarkerItemClick(annotation)
            true
        })


        val bitmap = convertDrawableToBitmap(
            AppCompatResources.getDrawable(this, R.drawable.red_marker))

        for (i in 0 until latitudeList.size) {
            var jsonObject = JSONObject()
            jsonObject.put("somevalue", i)
            val pointAnnotationOptions : PointAnnotationOptions = PointAnnotationOptions()
                .withPoint(Point.fromLngLat(longitudeList[i], latitudeList[i]))
                .withData(Gson().fromJson(jsonObject.toString(), JsonElement::class.java))
                .withIconImage(bitmap!!)

            markerList.add(pointAnnotationOptions)
        }
        pointAnnotationManager.create(markerList)
    }

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
            // copying drawable object to not manipulate on the same reference
            val constantState = sourceDrawable.constantState
            val drawable = constantState?.newDrawable()?.mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable!!.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }


    // marker click event
    private fun onMarkerItemClick(marker: PointAnnotation) {
        // get coordinates
        var location = marker.point.coordinates()

        AlertDialog.Builder(this)
            .setTitle("MarkerClick")
            .setMessage("Click" + "\n" + "$location" + "\n" + "$marker")
            .setPositiveButton("OK") {
                    dialog, _ -> dialog.dismiss()
            }.show()
    }







    private fun addAnnotationToMap() {
        // Create an instance of the Annotation API and get the PointAnnotationManager.
        bitmapFromDrawableRes(
            this@MainActivity,
            R.drawable.red_marker
        )?.let {
            val annotationApi = mapView.annotations
            val pointAnnotationManager = annotationApi.createPointAnnotationManager(mapView)
            // Set options for the resulting symbol layer.
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
            // Define a geographic coordinate.
                .withPoint(Point.fromLngLat(18.06, 59.31))
            // Specify the bitmap you assigned to the point annotation
            // The bitmap will be added to map style automatically.
                .withIconImage(it)
            // Add the resulting pointAnnotation to the map.
            pointAnnotationManager.create(pointAnnotationOptions)
        }
    }
    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))


}