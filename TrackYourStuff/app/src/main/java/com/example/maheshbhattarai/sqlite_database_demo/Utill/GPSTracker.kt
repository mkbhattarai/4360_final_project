package com.example.maheshbhattarai.sqlite_database_demo.Utill

import android.Manifest
import android.app.AlertDialog
import android.app.Service
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.util.Log

/**
 * Created by pc on 11/23/2017.
 */

class GPSTracker(private val mContext: Context) : Service(), LocationListener {

    // flag for GPS status
    internal var isGPSEnabled = false

    // flag for network status
    internal var isNetworkEnabled = false

    // flag for GPS status
    internal var canGetLocation = false

    internal var mlocation: Location? = null // location
    // location
    internal var latitude: Double = 0.toDouble() // latitude
    internal var longitude: Double = 0.toDouble() // longitude

    // Declaring a Location Manager
    protected var locationManager: LocationManager? = null

    // getting GPS status
    // getting network status
    // no network provider is enabled
    // First get location from Network Provider
    // TODO: Consider calling
    //    ActivityCompat#requestPermissions
    // here to request the missing permissions, and then overriding
    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
    //                                          int[] grantResults)
    // to handle the case where the user grants the permission. See the documentation
    // for ActivityCompat#requestPermissions for more details.
    // if GPS Enabled get lat/long using GPS Services


    val location: Location?
        get() {
            try {
                locationManager = mContext
                        .getSystemService(Context.LOCATION_SERVICE) as LocationManager
                isGPSEnabled = locationManager!!
                        .isProviderEnabled(LocationManager.GPS_PROVIDER)
                isNetworkEnabled = locationManager!!
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER)

                if (!isGPSEnabled && !isNetworkEnabled) {
                } else {
                    this.canGetLocation = true
                    if (isNetworkEnabled) {


                        Log.d("Network", "Network")
                        if (locationManager != null) {
                            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                            }
                            locationManager!!.requestLocationUpdates(
                                    LocationManager.NETWORK_PROVIDER,
                                    900000,
                                    1f, this)

                            mlocation = locationManager!!
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                            if (mlocation != null) {
                                latitude = mlocation!!.latitude
                                longitude = mlocation!!.longitude
                            }
                        }
                    }
                    if (isGPSEnabled) {
                        if (mlocation == null) {
                            locationManager!!.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER,
                                    900000,
                                    1f, this)
                            Log.d("GPS Enabled", "GPS Enabled")
                            if (locationManager != null) {
                                mlocation = locationManager!!
                                        .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                                if (mlocation != null) {
                                    latitude = mlocation!!.latitude
                                    longitude = mlocation!!.longitude
                                }
                            }
                        }
                    }
                }

                if (latitude == 0.0 && longitude == 0.0) {
                    try {
                        val criteria = Criteria()
                        val provider = locationManager!!.getBestProvider(criteria, false)
                        val location = locationManager!!.getLastKnownLocation(provider)
                        Log.d("ChangeLocation", provider)
                        Log.d("ChangeLocation1", location?.toString() ?: "NO LastLocation")
                        if (location != null) {
                            mlocation = location
                            latitude = mlocation!!.latitude
                            longitude = mlocation!!.longitude
                        }
                    } catch (e: SecurityException) {
                        e.printStackTrace()
                    }

                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return mlocation
        }

    init {
        location
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onLocationChanged(location: Location) {
        mlocation = location
        //        Log.e("Siddharth","Latitude :"+location.getLatitude() +" Longtitue :"+location.getLongitude());
        //        Utils.showToast(mContext,"Latitude :"+location.getLatitude() +" Longtitue :"+location.getLongitude() );
    }

    override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {

    }

    override fun onProviderEnabled(s: String) {

    }

    override fun onProviderDisabled(s: String) {

    }

    fun stopUsingGPS() {
        if (locationManager != null) {
            locationManager!!.removeUpdates(this@GPSTracker)
        }
    }

    /**
     * Function to get latitude
     */
    fun getLatitude(): Double {
        if (mlocation != null) {
            latitude = mlocation!!.latitude
            Log.e("Location Lat", "" + latitude)
        }

        // return latitude
        return latitude
    }

    /**
     * Function to get longitude
     */
    fun getLongitude(): Double {
        if (mlocation != null) {
            longitude = mlocation!!.longitude
            Log.e("Location Lat", "" + longitude)
        }
        return longitude
    }

    fun canGetLocation(): Boolean {
        return this.canGetLocation
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     */


    fun showSettingsAlert() {
        val alertDialog = AlertDialog.Builder(mContext)

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings")

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?")

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings") { dialog, which ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            mContext.startActivity(intent)
        }

        alertDialog.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }

        // Showing Alert Message
        alertDialog.show()

    }

    companion object {

        // The minimum distance to change Updates in meters
        private val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10 // 10 meters

        // The minimum time between updates in milliseconds
        private val MIN_TIME_BW_UPDATES = (1000 * 60 * 1).toLong() // 1 minute
    }
}
