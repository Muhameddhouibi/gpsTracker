package de.muhameddhouibi.gpstracker.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "tracking")
data class Track (
    var location: LatLng,
    var distance: Int = 0,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)