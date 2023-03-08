package com.example.mapboxtest

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import com.example.mapboxtest.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.layers.addLayerAbove
import com.mapbox.maps.extension.style.layers.addLayerBelow
import com.mapbox.maps.extension.style.layers.generated.CircleLayer
import com.mapbox.maps.extension.style.layers.generated.HeatmapLayer
import com.mapbox.maps.extension.style.layers.generated.circleLayer
import com.mapbox.maps.extension.style.layers.generated.heatmapLayer
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName
import com.mapbox.maps.extension.style.projection.generated.projection
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.gestures.addOnMapLongClickListener
import org.json.JSONObject

private const val TAG = "MainActivity_sindorim"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mapView: MapView
    private lateinit var mapboxMap: MapboxMap
    private lateinit var annotationApi: AnnotationPlugin
    private lateinit var annotationConfig: AnnotationConfig
    private lateinit var pointAnnotationManager: PointAnnotationManager
    var layerIDD = "map_annotation"

    var markerList: ArrayList<PointAnnotationOptions> = ArrayList()
    var latitudeList: ArrayList<Double> = ArrayList()
    var longitudeList: ArrayList<Double> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createLatLongForMarker()

        mapView = binding.mapView

        mapboxMap = mapView.getMapboxMap().apply {
            loadStyle(
                style(Style.DARK) {
                    +projection(ProjectionName.GLOBE)
                }) { style ->
                zoomCamera()
                annotationApi = mapView.annotations
                annotationConfig = AnnotationConfig(
                    layerId = layerIDD
                )
                // initialize point annotation manager
                pointAnnotationManager =
                    annotationApi.createPointAnnotationManager(annotationConfig)

                createDefaultMarkerOnMap()

                // init heatmap layer
                addRuntimeLayers(style)
            }
            addOnMapLongClickListener(
                object : OnMapLongClickListener {
                    override fun onMapLongClick(point: Point): Boolean {
                        Toast.makeText(
                            this@MainActivity,
                            "lat:${point.latitude()}\nlng:${point.longitude()}",
                            Toast.LENGTH_SHORT
                        ).show()
                        return true
                    }
                }
            )
        }
    }

    private fun zoomCamera() {
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder().center(
                Point.fromLngLat(
                    75.8577, 22.7196
                )
            )
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

    // Create Default Marker
    private fun createDefaultMarkerOnMap() {
        clearAnnotation()

        // Adding Click Event of Marker
        pointAnnotationManager.addClickListener(OnPointAnnotationClickListener { annotation ->
            onMarkerItemClick(annotation)
            true
        })

        val bitmap = convertDrawableToBitmap(
            AppCompatResources.getDrawable(this, R.drawable.red_marker)
        )

        for (i in 0 until latitudeList.size) {
            val jsonObject = JSONObject()
            jsonObject.put("somevalue", i)
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                .withPoint(Point.fromLngLat(longitudeList[i], latitudeList[i]))
                .withData(Gson().fromJson(jsonObject.toString(), JsonElement::class.java))
                .withIconImage(bitmap!!)

            markerList.add(pointAnnotationOptions)
        }
        pointAnnotationManager?.create(markerList)
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
        val location = marker.point.coordinates()

        AlertDialog.Builder(this)
            .setTitle("MarkerClick")
            .setMessage("Click" + "\n" + "$location" + "\n" + "$marker")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun addRuntimeLayers(style: Style) {
        style.addSource(createEarthquakeSource())
        style.addLayerAbove(createHeatmapLayer(), "waterway-label")
        style.addLayerBelow(createCircleLayer(), HEATMAP_LAYER_ID)
    }

    private fun createEarthquakeSource(): GeoJsonSource {
        return geoJsonSource(EARTHQUAKE_SOURCE_ID) {
            url(EARTHQUAKE_SOURCE_URL)
        }
    }

    private fun createHeatmapLayer(): HeatmapLayer {
        return heatmapLayer(
            HEATMAP_LAYER_ID,
            EARTHQUAKE_SOURCE_ID
        ) {
            maxZoom(9.0)
            sourceLayer(HEATMAP_LAYER_SOURCE)
            // Begin color ramp at 0-stop with a 0-transparancy color
            // to create a blur-like effect.
            heatmapColor(
                interpolate {
                    linear()
                    heatmapDensity()
                    stop {
                        literal(0)
                        rgba(33.0, 102.0, 172.0, 0.0)
                    }
                    stop {
                        literal(0.2)
                        rgb(103.0, 169.0, 207.0)
                    }
                    stop {
                        literal(0.4)
                        rgb(209.0, 229.0, 240.0)
                    }
                    stop {
                        literal(0.6)
                        rgb(253.0, 219.0, 240.0)
                    }
                    stop {
                        literal(0.8)
                        rgb(239.0, 138.0, 98.0)
                    }
                    stop {
                        literal(1)
                        rgb(178.0, 24.0, 43.0)
                    }
                }
            )
            // Increase the heatmap weight based on frequency and property magnitude
            heatmapWeight(
                interpolate {
                    linear()
                    get { literal("mag") }
                    stop {
                        literal(0)
                        literal(0)
                    }
                    stop {
                        literal(6)
                        literal(1)
                    }
                }
            )
            // Increase the heatmap color weight weight by zoom level
            // heatmap-intensity is a multiplier on top of heatmap-weight
            heatmapIntensity(
                interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0)
                        literal(1)
                    }
                    stop {
                        literal(9)
                        literal(3)
                    }
                }
            )
// Adjust the heatmap radius by zoom level
            heatmapRadius(
                interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0)
                        literal(2)
                    }
                    stop {
                        literal(9)
                        literal(20)
                    }
                }
            )
// Transition from heatmap to circle layer by zoom level
            heatmapOpacity(
                interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(7)
                        literal(1)
                    }
                    stop {
                        literal(9)
                        literal(0)
                    }
                }
            )
        }
    }

    private fun createCircleLayer(): CircleLayer {
        return circleLayer(
            CIRCLE_LAYER_ID,
            EARTHQUAKE_SOURCE_ID
        ) {
            circleRadius(
                interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(7)
                        interpolate {
                            linear()
                            get { literal("mag") }
                            stop {
                                literal(1)
                                literal(1)
                            }
                            stop {
                                literal(6)
                                literal(4)
                            }
                        }
                    }
                    stop {
                        literal(16)
                        interpolate {
                            linear()
                            get { literal("mag") }
                            stop {
                                literal(1)
                                literal(5)
                            }
                            stop {
                                literal(6)
                                literal(50)
                            }
                        }
                    }
                }
            )
            circleColor(
                interpolate {
                    linear()
                    get { literal("mag") }
                    stop {
                        literal(1)
                        rgba(33.0, 102.0, 172.0, 0.0)
                    }
                    stop {
                        literal(2)
                        rgb(102.0, 169.0, 207.0)
                    }
                    stop {
                        literal(3)
                        rgb(209.0, 229.0, 240.0)
                    }
                    stop {
                        literal(4)
                        rgb(253.0, 219.0, 199.0)
                    }
                    stop {
                        literal(5)
                        rgb(239.0, 138.0, 98.0)
                    }
                    stop {
                        literal(6)
                        rgb(178.0, 24.0, 43.0)
                    }
                }
            )
            circleOpacity(
                interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(7)
                        literal(0)
                    }
                    stop {
                        literal(8)
                        literal(1)
                    }
                }
            )
            circleStrokeColor("white")
            circleStrokeWidth(0.1)
        }
    }


    companion object {
        private const val EARTHQUAKE_SOURCE_URL =
            "https://www.mapbox.com/mapbox-gl-js/assets/earthquakes.geojson"
        private const val EARTHQUAKE_SOURCE_ID = "earthquakes"
        private const val HEATMAP_LAYER_ID = "earthquakes-heat"
        private const val HEATMAP_LAYER_SOURCE = "earthquakes"
        private const val CIRCLE_LAYER_ID = "earthquakes-circle"
    }

}