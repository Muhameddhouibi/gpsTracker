package de.muhameddhouibi.gpstracker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.muhameddhouibi.gpstracker.db.model.Converters
import de.muhameddhouibi.gpstracker.db.model.Track

@Database(entities = [Track::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getTrackerDao(): TrackerDao

}