package com.sindorim.beacontest

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver
import com.lemmingapex.trilateration.TrilaterationFunction
import org.altbeacon.beacon.Beacon
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.round

fun roundToTwoDecimalPlace(number: Double): Double {
    return round(number * 100) / 100
}

fun myDistance(txPower: Int, rssi: Double): Double {
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

fun trilateration(beacons: List<Beacon>): DoubleArray {
    val positions = mutableListOf<DoubleArray>()
    val distances = mutableListOf<Double>()
    for (i in beacons.indices) {
        val tempCoord = coordList.asSequence()
            .filter { coord ->
                coord.id == beacons[i].id3.toInt()
            }.first()
        positions.add(doubleArrayOf(tempCoord.x, tempCoord.y))
        distances.add(beacons[i].distance)
    }

    val solver = NonLinearLeastSquaresSolver(
        TrilaterationFunction(
            positions.toTypedArray(),
            distances.toDoubleArray()
        ), LevenbergMarquardtOptimizer()
    )

    val optimum: LeastSquaresOptimizer.Optimum = solver.solve()

    return optimum.point.toArray()
}