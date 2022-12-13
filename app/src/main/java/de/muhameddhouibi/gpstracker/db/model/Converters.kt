package de.muhameddhouibi.gpstracker.db.model

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng

class Converters {
    @TypeConverter
    fun latLngToString(latLng: LatLng) : String{
        return "(${latLng.latitude},${latLng.longitude}"
    }

    @TypeConverter
    fun stringToLatLng(string: String) : LatLng{
        val s = string.replace("(", "").replace(")", "")
        val list = s.split(",")
        return LatLng(list.first().toDouble(), list.last().toDouble())
    }
}

//TODO: update convertres to use byteArray instead of strings  for more content in your code