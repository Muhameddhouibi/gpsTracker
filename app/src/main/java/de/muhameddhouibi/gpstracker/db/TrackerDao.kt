package de.muhameddhouibi.gpstracker.db

import androidx.lifecycle.LiveData
import androidx.room.*
import de.muhameddhouibi.gpstracker.db.model.Track

@Dao
interface TrackerDao {
    @Query("select * from tracking")
    fun getAllTracks(): LiveData<List<Track>>

    @Insert()
    suspend fun insert(track: Track)

    @Update
    suspend fun update(track: Track)

    @Delete
    suspend fun delete(track: Track)

    @Query("SELECT distance FROM tracking ORDER BY id DESC LIMIT 1")
    fun getLastDistance(): LiveData<Int>
}