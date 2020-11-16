package org.imaginativeworld.simplemvvm.components

import android.Manifest
import android.app.Activity
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import timber.log.Timber

/**
 * This component used [FusedLocationProviderClient] to provide location update.
 * This is a Lifecycle-Aware Component. So it automatically start and stop location monitoring.
 *
 * Documentation about Lifecycle-Aware Component:
 * [Lifecycle-Aware Component](https://developer.android.com/topic/libraries/architecture/lifecycle)
 */
class LocationProviderUtilClient(
    private val activity: Activity,
    private val lifecycle: Lifecycle,
    private val locationRequestInterval: Long,
    private val locationRequestFastestInterval: Long,
    private val locationRequestPriority: Int = LocationRequest.PRIORITY_HIGH_ACCURACY,
    private val callback: (Location) -> Unit
) : LifecycleObserver {

    companion object {
        const val RESOLVE_REQUEST_CODE = 1
    }

    private var enabled = false
    private var locationUpdatedStarted = false

    private lateinit var locationCallback: LocationCallback
    private var locationRequest: LocationRequest? = null
    private var fusedLocationClient: FusedLocationProviderClient

    init {
        Timber.d("init")

        // Add to the lifecycle owner observer list.
        lifecycle.addObserver(this)

        // Init client and related things.
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(activity.application)

        initLocationCallback()
        createLocationRequest()
    }

    /**
     * Start location update.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        Timber.d("start")

        if (enabled) {
            // connect
            startLocationUpdates()
        }
    }

    /**
     * Stop location update.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        Timber.d("stop")

        // disconnect if connected
        stopLocationUpdate()
    }

    /**
     * Enable the component.
     */
    fun enable() {
        Timber.d("enable")

        enabled = true

        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            // connect if not connected
            if (!locationUpdatedStarted) {
                startLocationUpdates()
            }
        }
    }

    /**
     * Disable the component.
     */
    fun disable() {
        Timber.d("disable")

        enabled = false

        stopLocationUpdate()

    }

    /**
     * Check and send request for turn on the location service.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun requestForTurnOnLocation() {
        Timber.d("requestForTurnOnLocation")

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(
                locationRequest
                    ?: throw NullPointerException("Location request not initialized yet!")
            )

        LocationServices.getSettingsClient(activity)
            .checkLocationSettings(builder.build())
            .addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {

                    try {

                        exception.startResolutionForResult(
                            activity,
                            RESOLVE_REQUEST_CODE
                        )

                    } catch (sendEx: IntentSender.SendIntentException) {
                        sendEx.printStackTrace()

                        // Ignore the error
                    }

                }
            }
    }


    private fun initLocationCallback() {
        Timber.d("initLocationCallback")

        locationCallback = object : LocationCallback() {

            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return

                val currentLocation = locationResult.lastLocation

                Timber.d("currentLocation: $currentLocation")

                callback.invoke(currentLocation)
            }

        }

    }


    private fun createLocationRequest() {
        Timber.d("createLocationRequest")

        // Set up location request
        locationRequest = LocationRequest.create()?.apply {
            interval = locationRequestInterval
            fastestInterval = locationRequestFastestInterval
            priority = locationRequestPriority
        }

    }


    private fun startLocationUpdates() {
        Timber.d("startLocationUpdates")

        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            throw SecurityException("You does not have permission to access location.")
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

        locationUpdatedStarted = true

    }


    private fun stopLocationUpdate() {
        Timber.d("stopLocationUpdate")
        fusedLocationClient.removeLocationUpdates(locationCallback)

        locationUpdatedStarted = false
    }

}