package de.muhameddhouibi.gpstracker.db.repository

import androidx.lifecycle.LiveData
import de.muhameddhouibi.gpstracker.db.TrackerDao
import de.muhameddhouibi.gpstracker.db.model.Track
import javax.inject.Inject

class TrackerRepository @Inject constructor(
    private val  trackerDao: TrackerDao
) {

    fun getAllTracks() = trackerDao.getAllTracks()

    fun getLastDistance () = trackerDao.getLastDistance()

    suspend fun insert(track: Track) = trackerDao.insert(track = track)

}