/*
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * MVVM Pattern Source: https://github.com/ImaginativeShohag/Simple-MVVM
 */

package org.imaginativeworld.simplemvvm.components

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.activity.result.IntentSenderRequest
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.lifecycle.*
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

/**
 * This component used [FusedLocationProviderClient] to provide location update.
 * This is a Lifecycle-Aware Component. So it automatically start and stop location monitoring.
 *
 * Documentation about Lifecycle-Aware Component:
 * [Lifecycle-Aware Component](https://developer.android.com/topic/libraries/architecture/lifecycle)
 *
 * Notes:
 * - The provider is disabled by default.
 *
 * Methods:
 * - start()
 * - stop()
 * - enable()
 * - disable()
 *
 * - isLocationServiceOn()
 * - requestForTurnLocationOn()
 *
 * - Static: isLocationProviderGps()
 * - Static: isLocationPermissionGranted()
 */
enum class Ask {
    NONE, ONCE, ALWAYS
}

data class Options(
    // Ask user for turn on the location service on start.
    val askForTurnOnLocationService: Ask = Ask.ONCE,

    // Post location data just once
    var postOnce: Boolean = false
)

class LocationProviderUtilClient(
    private val activity: Activity,
    private val lifecycle: Lifecycle,
    private val options: Options = Options(),
    private val locationRequestInterval: Long = 15000,
    private val locationRequestFastestInterval: Long = 15000,
    private val locationRequestPriority: Int = LocationRequest.PRIORITY_HIGH_ACCURACY,
    private val smallestDisplacementInMeter: Float = 50f,
    var turnOnLocationCallback: ((IntentSenderRequest) -> Unit)? = null
) : DefaultLifecycleObserver {

    // Callback for posting the locations
    private val _callback: MutableStateFlow<Location?> = MutableStateFlow(null)
    val callback: StateFlow<Location?>
        get() = _callback

    private var alreadyAsked = false
    private var enabled = false
    private var locationUpdatedStarted = false

    private val locationCallback: LocationCallback
    private val locationRequest: LocationRequest
    private val fusedLocationClient: FusedLocationProviderClient

    init {
        Timber.d("init")

        // Add to the lifecycle owner observer list.
        lifecycle.addObserver(this)

        // Init location client
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(activity.application)

        // Set up location request
        locationRequest = LocationRequest.create().apply {
            interval = locationRequestInterval
            fastestInterval = locationRequestFastestInterval
            priority = locationRequestPriority
            smallestDisplacement = smallestDisplacementInMeter
        }

        // Init location callback
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val currentLocation = locationResult.lastLocation

                Timber.d("currentLocation: $currentLocation")

                _callback.value = currentLocation

                if (options.postOnce) {
                    disable()
                }
            }
        }
    }

    // ----------------------------------------------------------------
    // Lifecycle events
    // ----------------------------------------------------------------

    override fun onStart(owner: LifecycleOwner) {
        Timber.d("onStart")

        start()
    }

    override fun onStop(owner: LifecycleOwner) {
        Timber.d("onStop")

        stop()
    }

    // ----------------------------------------------------------------

    /**
     * Start location update.
     */
    @SuppressLint("MissingPermission")
    fun start() {
        Timber.d("start")

        if (enabled) {
            // Check and send request for turn on the location service on start.
            if (options.askForTurnOnLocationService != Ask.NONE) {
                requestForTurnLocationOn()
            }

            // connect
            if (!isLocationPermissionGranted(activity)) {
                throw SecurityException("You does not have permission to access location.")
            }

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )

            locationUpdatedStarted = true

            // post last known location
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                _callback.value = location
            }
        }
    }

    /**
     * Stop location update.
     */
    fun stop() {
        Timber.d("stop")

        // disconnect if connected
        fusedLocationClient.removeLocationUpdates(locationCallback)

        locationUpdatedStarted = false
    }

    // ----------------------------------------------------------------

    /**
     * Enable the component.
     */
    @RequiresPermission(
        anyOf = [
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION"
        ]
    )
    fun enable() {
        Timber.d("enable")

        if (!isLocationPermissionGranted(activity)) {
            throw SecurityException("You does not have permission to access location.")
        }

        enabled = true

        // Reset fields
        alreadyAsked = false

        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            // start if not started
            if (!locationUpdatedStarted) {
                start()
            }

            // post last known location
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                _callback.value = location
            }
        }
    }

    /**
     * Disable the component.
     */
    fun disable() {
        Timber.d("disable")

        enabled = false

        stop()
    }

    // ----------------------------------------------------------------

    /**
     * Check location service status.
     */
    fun isLocationServiceOn(listener: OnCompleteListener<LocationSettingsResponse>) {
        Timber.d("isLocationServiceOn")

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        LocationServices.getSettingsClient(activity)
            .checkLocationSettings(builder.build())
            .addOnCompleteListener(listener)
    }

    /**
     * Check and send request for turn on the location service.
     */
    fun requestForTurnLocationOn() {
        Timber.d("requestForTurnOnLocation")

        alreadyAsked = false

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        LocationServices.getSettingsClient(activity)
            .checkLocationSettings(builder.build())
            .addOnFailureListener { exception ->
                // Checking is ask once enabled and is already asked!
                if (options.askForTurnOnLocationService == Ask.ONCE && alreadyAsked) {
                    return@addOnFailureListener
                }

                alreadyAsked = true

                if (exception is ResolvableApiException) {
                    val intentSenderRequest = IntentSenderRequest.Builder(exception.resolution)
                        .build()

                    turnOnLocationCallback?.invoke(intentSenderRequest)
                }
            }
    }

    companion object {
        /**
         * Check if the location provider is GPS.
         */
        fun isLocationProviderGps(context: Context): Boolean {
            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
            return locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true
        }

        /**
         * Check location permission.
         *
         * @return True if permission granted.
         */
        fun isLocationPermissionGranted(context: Context): Boolean {
            Timber.d("isLocationPermissionGranted")

            return ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}
