package com.sindorim.beacontest

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.sindorim.beacontest.ui.theme.BeaconTestTheme
import org.altbeacon.beacon.Beacon

private const val TAG = "MainActivity_sdr"
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    @ExperimentalPermissionsApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeaconTestTheme {
                // A surface container using the 'background' color from the theme
                Home(mainViewModel)
            }
        }
    }
}

@ExperimentalPermissionsApi
@Composable
fun Home(mainViewModel: MainViewModel) {
    val btPermissionsState = rememberMultiplePermissionsState(
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_ADVERTISE,
            )
        } else {
            listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
            )
        },
        onPermissionsResult = {
            Log.d(TAG, "inPermission: Go")
            mainViewModel.scanToggle()
        }
    )// End of btPermissionsState

    Scaffold(
        topBar = {
            HomeTopAppBar(
                title = "Beacon Test",
                onClick = {
                    Log.d("SSAFY_PERMISSION", "Home: ${btPermissionsState.allPermissionsGranted}")
                    for (i in btPermissionsState.permissions.indices) {
                        Log.d(
                            "SSAFY_PERMISSION",
                            "${btPermissionsState.permissions[i]}: ${btPermissionsState.permissions[i].status}"
                        )
                    }

                    if (btPermissionsState.allPermissionsGranted) {
                        mainViewModel.scanToggle()
                    } else {
                        Log.d("SDR", "Home: permission?")
                        btPermissionsState.launchMultiplePermissionRequest()
                    }
                },
                actionTitle = if (!mainViewModel.isScan.value) {
                    "Start Scan"
                } else {
                    "Stop Scan"
                }
            ) // End of HomeTopAppBar
        },
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BeaconListView(mainViewModel)
            MyLocationTextView(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 8.dp),
                mainViewModel
            )
        }
    }
} // End of Home

@Composable
fun HomeTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    actionTitle: String
) {
    TopAppBar(
        title = { Text(text = title) },
        modifier = modifier,
        actions = {
            Button(
                onClick = { onClick.invoke() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
            ) {
                Text(text = actionTitle, modifier = Modifier.background(Color.Transparent))
            }
        }
    )
} // End of HomeTopAppBar

@Composable
fun MyLocationTextView(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
        ) {
        Column(modifier = modifier) {
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Black.copy(0.8f),
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("My Location")
                }
            })
            Text(
                text = "${roundToTwoDecimalPlace(mainViewModel.nowLocation.value[0])}, " +
                        "${roundToTwoDecimalPlace(mainViewModel.nowLocation.value[1])}, " +
                        "${roundToTwoDecimalPlace(mainViewModel.nowLocation.value[2])}"
            )
        }
        Column(modifier = modifier) {
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Black.copy(0.8f),
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("Kalman Location")
                }
            })
            Text(
                text = "${roundToTwoDecimalPlace(mainViewModel.kalmanLocation.value[0])}, " +
                        "${roundToTwoDecimalPlace(mainViewModel.kalmanLocation.value[1])}, " +
                        "${roundToTwoDecimalPlace(mainViewModel.kalmanLocation.value[2])}"
            )
        }
    }
}

@Composable
fun BeaconListView(mainViewModel: MainViewModel) {
    val beacons = mainViewModel.beaconList.value
    LazyColumn(modifier = Modifier.fillMaxHeight(0.8f)) {
        items(beacons) { beacon ->
            ListCard(beacon)
        }
    }
} // End of BeaconListView

@Composable
fun ListCard(beacon: Beacon) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 8.dp,
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Text(text = "Major: ${beacon.id2}")
            Text(text = "Minor: ${beacon.id3}")
            Text(text = "Distance: ${roundToTwoDecimalPlace(beacon.distance)} meters")
            Text(text = "mDistance: ${(myDistance(beacon.txPower, beacon.runningAverageRssi))}")
            Text(text = "Rssi: ${beacon.rssi}")
            Text(text = "Rssi: ${beacon.runningAverageRssi}")
        }
    }
}