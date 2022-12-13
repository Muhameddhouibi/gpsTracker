package de.muhameddhouibi.gpstracker.presentation.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import de.muhameddhouibi.gpstracker.R
import de.muhameddhouibi.gpstracker.db.model.Track
import de.muhameddhouibi.gpstracker.presentation.viewmodel.TrackingViewModel
import de.muhameddhouibi.gpstracker.service.Polyline
import de.muhameddhouibi.gpstracker.service.TrackingService
import de.muhameddhouibi.gpstracker.utils.Constants.ACTION_PAUSE_SERVICE
import de.muhameddhouibi.gpstracker.utils.Constants.ACTION_START_OR_RESUME_SERVICE
import de.muhameddhouibi.gpstracker.utils.Constants.ACTION_STOP_SERVICE
import de.muhameddhouibi.gpstracker.utils.TrackingUtility.calculatePolylineLength
import kotlinx.android.synthetic.main.fragment_tracking.*


@AndroidEntryPoint
class TrackingFragment : Fragment (R.layout.fragment_tracking){

    private var isTracking = false
    private var pathPoints = mutableListOf<Polyline>()

    private val viewModel: TrackingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ActivityCompat.requestPermissions(
            this.requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            0
        )
        btnToggleTrack.setOnClickListener {
            toggleTrack()
        }
        btnFinishTrack.setOnClickListener {
            tvDistance.text = "0 meters"
            sendCommandToService(ACTION_STOP_SERVICE)
        }


        subscribeToObservers()
        super.onViewCreated(view, savedInstanceState)
    }
    private fun subscribeToObservers() {
        TrackingService.isTracking.observe(viewLifecycleOwner, Observer {
            updateTracking(it)
        })

        TrackingService.pathPoints.observe(viewLifecycleOwner, Observer {
            pathPoints = it
        })

        viewModel.lastDistance.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val distanceTxt = "$it m"
                tvDistance.text = distanceTxt
            } else {
                tvDistance.text = "0 m"
            }
        })
    }

    private fun toggleTrack() {
        if(isTracking) {
            sendCommandToService(ACTION_PAUSE_SERVICE)
            endTrackandSaveToDb()
        } else {
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        }
    }
    @SuppressLint("SetTextI18n")
    private fun updateTracking(isTracking: Boolean) {
        this.isTracking = isTracking
        if(!isTracking) {
            btnToggleTrack.text = resources.getString(R.string.start)
            btnFinishTrack.visibility = View.VISIBLE
        } else {
            btnToggleTrack.text = resources.getString(R.string.stop)
            btnFinishTrack.visibility = View.GONE
        }
    }

    private fun endTrackandSaveToDb() {
        var distanceInMeters = 0
            for(polyline in pathPoints) {
                if (polyline.isNotEmpty()){
                    distanceInMeters = calculatePolylineLength(polyline).toInt()
                    val track = Track(LatLng(polyline.last().latitude,polyline.last().longitude),distanceInMeters)
                    viewModel.insertTrack(track)
                }
            }

    }

    private fun sendCommandToService(action: String) =
        Intent(requireContext(), TrackingService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }
}