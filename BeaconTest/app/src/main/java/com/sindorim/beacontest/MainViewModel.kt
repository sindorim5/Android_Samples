package com.sindorim.beacontest

import android.annotation.SuppressLint
import android.app.Application
import android.bluetooth.BluetoothManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.altbeacon.beacon.*
import org.apache.commons.math3.filter.KalmanFilter
import java.util.*

private const val TAG = "MainViewModel_SSAFY"

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var beaconList: MutableState<List<Beacon>> = mutableStateOf(emptyList())
    var nowLocation = mutableStateOf(listOf(0.0, 0.0, 0.0))
    var kalmanLocation = mutableStateOf(listOf(0.0, 0.0, 0.0))
    val isScan = mutableStateOf(false)

    private val beaconManager = BeaconManager.getInstanceForApplication(application)
    private val region = Region(
        "estimote",
        Identifier.parse(BEACON_UUID),
        null,
        null
    )

    fun scanToggle() {
        if (!isScan.value) {
            isScan.value = true
            beaconManager.apply {
                beaconParsers.add(BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"))
                addMonitorNotifier(monitorNotifier)
                addRangeNotifier(rangeNotifier)
                startMonitoring(region)
                startRangingBeacons(region)
            }
        } else {
            isScan.value = false
            beaconManager.stopMonitoring(region)
            beaconManager.stopRangingBeacons(region)
        }
    } // End of scanToggle

    private var monitorNotifier: MonitorNotifier = object : MonitorNotifier {
        override fun didEnterRegion(region: Region) { //발견 함.
            Log.d(TAG, "I just saw an beacon for the first time!")
        }

        override fun didExitRegion(region: Region) { //발견 못함.
            Log.d(TAG, "I no longer see an beacon")
        }

        override fun didDetermineStateForRegion(state: Int, region: Region) { //상태변경
            Log.d(TAG, "I have just switched from seeing/not seeing beacons: $state")
        }
    } // End of monitorNotifier

    //매초마다 해당 리전의 beacon 정보들을 collection으로 제공받아 처리한다.
    private var rangeNotifier: RangeNotifier = object : RangeNotifier {
        override fun didRangeBeaconsInRegion(beacons: MutableCollection<Beacon>?, region: Region?) {
            beacons?.run {
                beaconList.value = beacons.toList()
                getMyLocation(beacons.toList())
            }
        }
    } // End of rangeNotifier

    private val kalmanFilter = KalmanFilter3D(
        initialState = listOf(0.0, 0.0, 0.0),
        initialCovariance = listOf(
            listOf(1.0, 0.0, 0.0),
            listOf(0.0, 1.0, 0.0),
            listOf(0.0, 0.0, 1.0)
        )
    )

    fun getMyLocation(beacons: List<Beacon>) {
        if (beacons.size < 3) {
            Log.d(TAG, "loc_0: $beacons")
            return
        } else {
            var sortedList = beacons.sortedBy { it.distance }
            var centroid = trilateration(sortedList).toList()

            if (centroid == listOf(-9999.9, -9999.9, -9999.9)) {
                return
            }

            // Apply the Kalman filter to the centroid
            val measurementNoise =
                listOf(1.0, 1.0, 1.0) // Adjust this value based on your measurement noise
            val filteredCentroid = kalmanFilter.update(centroid, measurementNoise)

            for (i in centroid.indices) {
                Log.d(TAG, "loc_cen${i}: ${centroid[i]}")
                Log.d(TAG, "loc_kal${i}: ${filteredCentroid[i]}")
            }

            nowLocation.value = centroid
            kalmanLocation.value = filteredCentroid
            return
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 8
        private const val BEACON_UUID = "E2C56DB5-DFFB-48D2-B060-D0F5A71096E0"
        private const val BEACON_MAJOR = "40011"
        private const val BEACON_MINOR = "33158"
        private const val BLUETOOTH_ADDRESS = "AC:23:3F:F6:BD:46"
        private const val BEACON_DISTANCE = 5.0
    }
}