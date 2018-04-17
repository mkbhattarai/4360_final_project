package com.example.maheshbhattarai.sqlite_database_demo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import butterknife.internal.Utils
import com.example.maheshbhattarai.sqlite_database_demo.Utill.Apprefence
import com.example.maheshbhattarai.sqlite_database_demo.Utill.GPSTracker
import com.example.maheshbhattarai.sqlite_database_demo.Utill.LocationNotifyService
import com.example.maheshbhattarai.sqlite_database_demo.database.DBHelper
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener

// Jesus can comment now.
class MainActivity : AppCompatActivity() {
    var TAG : String =   "MainActivity"
    lateinit var btn_androidmap: Button
    lateinit var btn_androidsqlite: Button
    private val PERMISSION_REQUEST_CODE = 1
    lateinit  var db : DBHelper
    private var lati: Double ? =0.0
    private var longg: Double ? =0.0
    lateinit var mLocationRequest: LocationRequest
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit var mLocationCallback: LocationCallback
    lateinit var mGeoDataClient: GeoDataClient
    lateinit var mSettingsClient: SettingsClient
    lateinit var mLocationSettingsRequest: LocationSettingsRequest
    private val REQUEST_CHECK_SETTINGS = 0x1
    var isAutoCompleteLocation = false
    lateinit var location: Location
    val REQUEST_LOCATION = 1011

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var user_id = Apprefence.getUserId(this)
        var roie = Apprefence.getRole(this)
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval((10 * 1000).toLong())        // 10 seconds, in milliseconds
                .setFastestInterval((6 * 1000).toLong()) // 1 second, in milliseconds

        mSettingsClient = LocationServices.getSettingsClient(this)
        val builder = LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest)
        mLocationSettingsRequest = builder.build()
        if (user_id != "") {
            if (roie == "0") {
                val intent = Intent(this, DashBroadActivity::class.java)
                startActivity(intent)
            } else if (roie == "1") {
                val intent = Intent(this, JobSeekerActivity::class.java)
                startActivity(intent)
            }
        }


//        startService(Intent(this,LocationNotifyService::class.java))
        btn_androidmap = findViewById(R.id.btn_androidmap)
        btn_androidsqlite = findViewById(R.id.btn_androidsqlite)

        btn_androidmap.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SearchPlacesActivity::class.java)
            startActivity(intent)
            finish()
        })

        btn_androidsqlite.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        })
    }


    private fun getlat_lng() {
        val gps = GPSTracker(this)
        if (gps.canGetLocation()) {
            if (gps.getLatitude() !== 0.0 && gps.getLongitude() !== 0.0) {
                lati = gps.getLatitude() // returns latitude
                longg = gps.getLongitude()
                val pickuplat = lati.toString()
                val pickuplong = longg.toString()

            }
        }
    }

    @SuppressLint("LongLogTag")
    private fun requestPermission(): Boolean {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)
                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)
                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_NETWORK_STATE)
                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CHANGE_NETWORK_STATE)
                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_WIFI_STATE)
                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CHANGE_WIFI_STATE)) {
            Apprefence.setUserId(this,"1")
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.CHANGE_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE), PERMISSION_REQUEST_CODE)
        }
        return true
    }

    @SuppressLint("LongLogTag")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG + "Splash 1", "onRequestPermissionsResult()")
            } else {
                Toast.makeText(applicationContext, "Permission Denied, You cannot access Storage data.", Toast.LENGTH_LONG).show()

            }
        }
    }

    fun hideKeyboard() {
        try {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == REQUEST_LOCATION) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                initLocation()
//            } else {
//                Toast.makeText(this@MainActivity, "Permission Denied", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION)
    }

    override fun onResume() {
        super.onResume()
        if (checkPermissions()) {
            initLocation()

        } else {
            requestPermission()
            requestPermissions();
        }
    }

    private fun initLocation() {
        try {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this@MainActivity)
//            getLastLocation()
            try {

                mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                        .addOnSuccessListener(this, object : OnSuccessListener<LocationSettingsResponse> {
                            override fun onSuccess(p0: LocationSettingsResponse?) {
//                                mFusedLocationClient.requestLocationUpdates(mLocationRequest,
//                                        mLocationCallback, Looper.myLooper());
                            }

                        }).addOnFailureListener(this, object : OnFailureListener {
                            override fun onFailure(p0: java.lang.Exception) {
                                val statusCode = (p0 as ApiException).getStatusCode();
                                when (statusCode) {
                                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                                        Log.i("Location", "Location settings are not satisfied. Attempting to upgrade " +
                                                "location settings ");
                                        try {
                                            // Show the dialog by calling startResolutionForResult(), and check the
                                            // result in onActivityResult().
                                            val rae = p0 as ResolvableApiException
                                            rae.startResolutionForResult(this@MainActivity, REQUEST_CHECK_SETTINGS);
                                        } catch (sie: IntentSender.SendIntentException) {
                                            Log.i("Location", "PendingIntent unable to execute request.");
                                        }
                                    }

                                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE ->
                                        Toast.makeText(this@MainActivity, "Location settings are inadequate, and cannot be \"+\n" +
                                                "                                    \"fixed here. Fix in Settings.", Toast.LENGTH_LONG).show();


                                }
                            }

                        })

            } catch (unlikely: SecurityException) {
                Log.e("Location", "Lost location permission. Could not request updates. " + unlikely)
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }




}
