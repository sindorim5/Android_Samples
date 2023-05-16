package com.sindorim.lifecycletest.screens

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.sindorim.lifecycletest.SecondActivity

private const val TAG = "SecondScreen_SDR"

@Composable
fun SecondScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    navController: NavController
) {

    val context = LocalContext.current

    DisposableEffect(key1 = lifecycleOwner.lifecycle) {

        Log.d(TAG, "SecondScreen1: ${lifecycleOwner.lifecycle.currentState}")

        val observer = LifecycleEventObserver { source, event ->
            Log.d(TAG, "SecondScreen observe: $event")
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            Log.d(TAG, "SecondScreen: onDispose")
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Second Screen",
        )
        Button(onClick = {
            val intent = Intent(context, SecondActivity::class.java)
            context.startActivity(intent)
        }) {
            Text(
                text = "To Second Activity"
            )
        }
    }
}