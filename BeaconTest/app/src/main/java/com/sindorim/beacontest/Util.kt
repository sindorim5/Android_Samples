package com.sindorim.beacontest

import android.util.Log
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.round

fun roundToTwoDecimalPlace(number: Double) : Double {
    return round(number * 100) / 100
}

fun myDistance(txPower: Int, rssi: Double) : Double {
    if (rssi == 0.0) {
        return -1.0
    }
    val ten = 10.0

    val divider = if (rssi < 70.0) {
        16.0
    } else if (rssi >= 70.0 && rssi < 80) {
        35.0
    } else {
        45.0
    }
    val power = (abs(rssi) - abs(txPower)) / (10 * divider)
    val result = roundToTwoDecimalPlace(ten.pow(power))

    return result
}