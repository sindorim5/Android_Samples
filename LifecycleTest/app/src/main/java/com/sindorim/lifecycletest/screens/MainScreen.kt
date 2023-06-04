package com.sindorim.lifecycletest.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.sindorim.lifecycletest.navigation.Screens

private const val TAG = "MainScreen_SDR"

@Composable
fun MainScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    navController: NavController
) {

    Log.d(TAG, "MainScreen: gogogo")

    DisposableEffect(key1 = lifecycleOwner.lifecycle) {

        val observer = LifecycleEventObserver { source, event ->
            Log.d(TAG, "MainScreen observe: $event")
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            Log.d(TAG, "MainScreen: onDispose")
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Main Screen",
            )
            Button(onClick = {
                navController.navigate(Screens.Second.route)
            }) {
                Text(
                    text = "To Second Screen"
                )
            }
        }
    }

}