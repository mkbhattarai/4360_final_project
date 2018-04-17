package com.example.maheshbhattarai.sqlite_database_demo.Utill

import android.Manifest
import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.ActivityCompat
import android.util.Log

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices


class LocationNotifyService : Service(), android.location.LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    internal lateinit var mLocationRequest: LocationRequest
    internal lateinit var mGoogleApiClient: GoogleApiClient
    private var now: Long? = null
    internal var locMgr: LocationManager? = null
    internal var sharedPreferences: SharedPreferences? = null
    internal var context: Context? = null
    internal var flag = ""
    internal var time = ""

    //Check Google play is available or not
    private val isGooglePlayServicesAvailable: Boolean
        get() {
            val status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(applicationContext)
            return ConnectionResult.SUCCESS == status
        }

    internal var isGPSEnabled = false
    internal var isNetworkEnabled = false
    var canGetLocation = false

    /*if(location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }*/ val location: Location?
        get() {
            var bestResult: Location? = null
            var locatinGps: Location? = null
            var locationNetwork: Location? = null
            var lastFusedLocation: Location? = null

            try {
                isGPSEnabled = locMgr!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
                isNetworkEnabled = locMgr!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                lastFusedLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)

                Log.e("fused", lastFusedLocation!!.toString() + "")

                if (!isGPSEnabled && !isNetworkEnabled) {

                } else {
                    this.canGetLocation = true
                    if (isNetworkEnabled) {
                        locMgr!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 900000, 1f, this)
                        if (locMgr != null) {
                            locationNetwork = locMgr!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                            Log.e("network", locationNetwork!!.toString() + "")
                        }
                    }
                    if (isGPSEnabled) {
                        locMgr!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 900000, 1f, this)
                        if (locMgr != null) {
                            locatinGps = locMgr!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                            Log.e("gps", locatinGps!!.toString() + "")
                        }
                    }
                }
                if (locatinGps != null && locationNetwork != null) {
                    Log.e("time", locatinGps.time.toString() + ", " + locationNetwork.time)
                    if (locatinGps.time > locationNetwork.time) {
                        bestResult = locatinGps
                    } else if (locatinGps.time < locationNetwork.time) {
                        bestResult = locationNetwork
                    }
                } else if (locatinGps != null) {
                    bestResult = locatinGps
                } else if (locationNetwork != null) {
                    bestResult = locationNetwork
                }
                if (bestResult != null && lastFusedLocation != null) {
                    if (bestResult.time < lastFusedLocation.time)
                        bestResult = lastFusedLocation
                }
            } catch (e: SecurityException) {
            }

            Log.e("best location", "" + bestResult + "")

            return bestResult
        }

    @SuppressLint("RestrictedApi")
    override fun onCreate() {
        //show error dialog if GoolglePlayServices not available

        if (isGooglePlayServicesAvailable) {
            mLocationRequest = LocationRequest()
            mLocationRequest.interval = 900000
            // mLocationRequest.setFastestInterval(1);
            mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            //mLocationRequest.setSmallestDisplacement(10.0f);  /* min dist for location change, here it is 10 meter */
            mGoogleApiClient = GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build()
            mGoogleApiClient.connect()
            locMgr = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            Log.e("Oncreate is here......", locMgr.toString())
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        now = java.lang.Long.valueOf(System.currentTimeMillis())
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onConnected(bundle: Bundle?) {
        startLocationUpdates()
    }

    override fun onConnectionSuspended(i: Int) {}

    protected fun startLocationUpdates() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            val pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest) { location ->
                var location = location
                Log.e("fusedListener", location!!.toString() + "")
                location = location
                saveLocation(location)
            }
        } catch (e: IllegalStateException) {
        }

    }

    private fun saveLocation(location: Location?) {

        try {
            if (location!!.latitude != 0.0 && location.longitude != 0.0) {
                Log.e("LatLng: ", location.latitude.toString() + ", " + location.longitude)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onLocationChanged(location: Location?) {
        var location = location
        Log.e("otherlister 1", location!!.toString() + "")

        if (location != null) {
            Log.e("otherlister 2", location.toString() + "")
            location = location
            saveLocation(location)
        } else {
            Log.e("otherlister 3", location.toString() + "")

            location = location
            saveLocation(location)
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}

    override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {}

    override fun onProviderEnabled(s: String) {}

    override fun onProviderDisabled(s: String) {}

    companion object {
        var mCurrentLocation: Location? = null
        val MyPREFERENCES = "MyPrefs"
    }
    //

}