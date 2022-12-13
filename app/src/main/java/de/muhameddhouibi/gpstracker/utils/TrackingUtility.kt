package de.muhameddhouibi.gpstracker.utils

import android.location.Location
import de.muhameddhouibi.gpstracker.service.Polyline


object TrackingUtility {

    fun calculatePolylineLength(polyline: Polyline): Float {
        var distance = 0f
        for(i in 0..polyline.size - 2) {
            val pos1 = polyline[i]
            val pos2 = polyline[i + 1]

            val result = FloatArray(1)
            Location.distanceBetween(
                pos1.latitude,
                pos1.longitude,
                pos2.latitude,
                pos2.longitude,
                result
            )
            distance += result[0]
        }
        return distance
    }
}
















