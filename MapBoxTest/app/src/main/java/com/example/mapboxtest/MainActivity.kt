package com.example.mapboxtest

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.example.mapboxtest.databinding.ActivityMainBinding
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mapView = binding.mapView.apply {
            getMapboxMap().loadStyleUri(Style.DARK,
            object : Style.OnStyleLoaded {
                override fun onStyleLoaded(style: Style) {
                    addAnnotationToMap()
                }
            })
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

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
            // copying drawable object to not manipulate on the same reference
            val constantState = sourceDrawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }
}