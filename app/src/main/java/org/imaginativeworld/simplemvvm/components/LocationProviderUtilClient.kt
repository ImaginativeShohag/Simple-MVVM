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
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.lifecycle.*
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener
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
 * - Variable: askOnceForTurnOnLocationService
 * - isLocationServiceOn()
 * - requestForTurnOnLocation()
 * - enableRequestForLocationTurnOn()
 * - Static: getLocationMode()
 * - Static: isLocationPermissionGranted()
 *
 * todo: better documentation
 */
class LocationProviderUtilClient(
    private val activity: Activity,
    private val lifecycle: Lifecycle,
    private val locationRequestInterval: Long,
    private val locationRequestFastestInterval: Long,
    private val locationRequestPriority: Int = LocationRequest.PRIORITY_HIGH_ACCURACY,
) : LifecycleObserver {

    // Callback for giving the locations
    private val _callback: MutableLiveData<Location> = MutableLiveData()
    val callback: LiveData<Location>
        get() = _callback

    // Ask to turn on the location only once
    var askOnceForTurnOnLocationService = true

    // ----------------------------------------------------------------
    // Variables
    // ----------------------------------------------------------------
    private var askForLocationTurnOn: Boolean = false
    private var locationSettingsResolutionRequestCode: Int? = null

    private var alreadyAsked = false

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

        // Reset fields
        alreadyAsked = false

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
     * Check is location service on.
     */
    fun isLocationServiceOn(listener: OnCompleteListener<LocationSettingsResponse>) {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(
                locationRequest
                    ?: throw NullPointerException("Location request not initialized yet!")
            )

        LocationServices.getSettingsClient(activity)
            .checkLocationSettings(builder.build())
            .addOnCompleteListener(listener)
    }

    /**
     * Check and send request for turn on the location service on start.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStartRequestForTurnOnLocation() {
        Timber.d("onStartRequestForTurnOnLocation")

        if (!askForLocationTurnOn) {
            return
        }

        requestForTurnOnLocation()
    }

    /**
     * Check and send request for turn on the location service.
     */
    fun requestForTurnOnLocation() {
        alreadyAsked = false

        sendRequestForTurnOnLocation()
    }

    /**
     * Ask user for turn on the location service.
     */
    fun enableRequestForLocationTurnOn(
        resolutionRequestCode: Int
    ) {
        askForLocationTurnOn = true
        locationSettingsResolutionRequestCode = resolutionRequestCode

        requestForTurnOnLocation()
    }

    /**
     * Check and send request for turn on the location service.
     */
    private fun sendRequestForTurnOnLocation() {
        Timber.d("requestForTurnOnLocation")

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(
                locationRequest
                    ?: throw NullPointerException("Location request not initialized yet!")
            )

        LocationServices.getSettingsClient(activity)
            .checkLocationSettings(builder.build())
            .addOnFailureListener { exception ->
                // Checking is ask once enabled and is already asked!
                if (askOnceForTurnOnLocationService && alreadyAsked) {
                    return@addOnFailureListener
                }

                alreadyAsked = true

                if (exception is ResolvableApiException) {
                    try {
                        exception.startResolutionForResult(
                            activity,
                            locationSettingsResolutionRequestCode ?: throw Exception("Resolution request code not provided!")
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

                _callback.value = currentLocation

                if (askOnceForTurnOnLocationService) {
                    alreadyAsked = true
                    disable()
                }
            }
        }
    }

    private fun createLocationRequest() {
        Timber.d("createLocationRequest")

        // Set up location request
        locationRequest = LocationRequest.create().apply {
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

    companion object {
        fun getLocationMode(activity: Activity): Int {
            /**
             * 0 = Settings.Secure.LOCATION_MODE_OFF
            1 = Settings.Secure.LOCATION_MODE_SENSORS_ONLY
            2 = Settings.Secure.LOCATION_MODE_BATTERY_SAVING
            3 = Settings.Secure.LOCATION_MODE_HIGH_ACCURACY
             */
            return Settings.Secure.getInt(
                activity.contentResolver,
                Settings.Secure.LOCATION_MODE
            )
        }

        /**
         * Check location permission.
         *
         * @return True if permission granted.
         */
        fun isLocationPermissionGranted(context: Context): Boolean {
            return ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}
