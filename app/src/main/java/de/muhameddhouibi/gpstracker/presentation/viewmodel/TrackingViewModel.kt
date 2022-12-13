package de.muhameddhouibi.gpstracker.presentation.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.muhameddhouibi.gpstracker.db.model.Track
import de.muhameddhouibi.gpstracker.db.repository.TrackerRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackingViewModel
@Inject constructor(
    private val trackerRepository: TrackerRepository,
): ViewModel() {

    private val getAllTracks = trackerRepository.getAllTracks()
    val lastDistance = trackerRepository.getLastDistance()

    val tracks = MediatorLiveData<List<Track>>()

    init {
        tracks.addSource(getAllTracks) { result ->
            result?.let { tracks.value = it }
        }
    }

    fun insertTrack(track: Track) = viewModelScope.launch {
        trackerRepository.insert(track)
    }





}