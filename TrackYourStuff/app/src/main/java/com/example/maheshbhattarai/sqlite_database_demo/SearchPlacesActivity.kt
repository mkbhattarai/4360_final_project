package com.example.maheshbhattarai.sqlite_database_demo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Point
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.ahmadrosid.lib.drawroutemap.DrawMarker
import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps
import com.example.maheshbhattarai.sqlite_database_demo.Utill.GPSTracker
import com.example.maheshbhattarai.sqlite_database_demo.adapter.PlacesAdapter
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.location.places.PlaceBufferResponse
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_map.*
import java.util.*

class SearchPlacesActivity : AppCompatActivity(), OnMapReadyCallback {


    var googleMap: GoogleMap? = null
    lateinit var placesAdapter: PlacesAdapter
    lateinit var DestinationlatLng: LatLng
    lateinit var CurrentlatLng: LatLng
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
    var selectedLocation = ""



    var currentLatitute: Double = 0.0
    var currentLongtitute: Double = 0.0

    var destinationlatitude: Double = 0.0
    var destinationlongitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        MapsInitializer.initialize(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_image) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mGeoDataClient = Places.getGeoDataClient(this, null);

        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                val loc = locationResult!!.lastLocation
                if (!isAutoCompleteLocation) {
                    location = loc
                    CurrentlatLng = LatLng(location.latitude, location.longitude)
                    Log.e("CurrentlatLng", "lat " + location.latitude.toString() + " long" + location.longitude.toString());
                    currentLatitute = location.latitude
                    currentLongtitute = location.longitude

                   /* val options = MarkerOptions()
                            .position(CurrentlatLng)
                    googleMap?.apply {
                        addMarker(options)
                        moveCamera(CameraUpdateFactory.newLatLng(CurrentlatLng))
                        animateCamera(CameraUpdateFactory.newLatLngZoom(CurrentlatLng, 15f))
                    }*/

                    //assignToMap()
                }
            }

        }

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval((10 * 1000).toLong())        // 10 seconds, in milliseconds
                .setFastestInterval((6 * 1000).toLong()) // 1 second, in milliseconds

        mSettingsClient = LocationServices.getSettingsClient(this)
        val builder = LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest)
        mLocationSettingsRequest = builder.build()

        val BOUNDS_INDIA = LatLngBounds(LatLng(currentLatitute, currentLongtitute), LatLng(destinationlatitude, destinationlongitude))
        placesAdapter = PlacesAdapter(this, android.R.layout.simple_list_item_1, mGeoDataClient, null, BOUNDS_INDIA)
        enter_place.setAdapter(placesAdapter)
        enter_place.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count > 0) {
                    //cancel.visibility = View.VISIBLE
                } else {
                    //cancel.visibility = View.GONE
                }
            }
        })
        enter_place.setOnItemClickListener({ parent, view, position, id ->
            //getLatLong(placesAdapter.getPlace(position))
            hideKeyboard()
            val item = placesAdapter.getItem(position)
            val placeId = item?.getPlaceId()
            val primaryText = item?.getPrimaryText(null)

            Log.i("Autocomplete", "Autocomplete item selected: " + primaryText)


            val placeResult = mGeoDataClient.getPlaceById(placeId)
            placeResult.addOnCompleteListener(object : OnCompleteListener<PlaceBufferResponse> {
                @SuppressLint("RestrictedApi")
                override fun onComplete(task: Task<PlaceBufferResponse>) {
                    val places = task.getResult();
                    val place = places.get(0)

                    isAutoCompleteLocation = true
                    DestinationlatLng = place.latLng
                    Log.e("DestinationlatLng", "dest lat" + place.latLng.latitude.toString() + " dest long "+place.latLng.longitude.toString())
                    destinationlatitude = place.latLng.latitude
                    destinationlongitude = place.latLng.longitude

                    assignToMap()

                    places.release()
                }

            })

           /* Toast.makeText(applicationContext, "Clicked: " + primaryText,
                    Toast.LENGTH_SHORT).show()*/
        })


       /* btn_next.setOnClickListener {
            assignToMap()
        }*/

        /*cancel.setOnClickListener {
            enter_place.setText("")
        }*/
    }

    private fun assignToMap() {
        googleMap?.clear()


        try {
            val mallLoc = Location("")
            mallLoc.latitude = currentLatitute
            mallLoc.longitude = currentLongtitute

            val userLoc = Location("")
            userLoc.latitude = destinationlatitude
            userLoc.longitude = destinationlongitude

            val distance = mallLoc.distanceTo(userLoc) / 1000
            showMiles.setText(String.format(Locale.getDefault(), "%.2f", distance) + " km Away")
            Log.e("distance",String.format(Locale.getDefault(), "%.2f", distance) + " km Away")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val origin = LatLng(currentLatitute, currentLongtitute);
        val destination = LatLng(destinationlatitude, destinationlongitude);

        DrawRouteMaps.getInstance(this)
                .draw(origin, destination, googleMap);
        DrawMarker.getInstance(this).draw(googleMap, origin, R.drawable.map_pin, "");
        DrawMarker.getInstance(this).draw(googleMap, destination, R.drawable.map_pin, "");

        val positions = listOf(
                LatLng(currentLatitute, currentLongtitute),
                LatLng(destinationlatitude, destinationlongitude)
        )


        val latLngBounds = positions.fold(LatLngBounds.Builder(), LatLngBounds.Builder::include)
                .build()
        val displaySize = Point()
        getWindowManager().getDefaultDisplay().getSize(displaySize);
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, displaySize.x, 180, 15));


        /*val options = MarkerOptions()
                .position(latLng)
                .title("My Location")
        googleMap?.apply {
            addMarker(options)
            moveCamera(CameraUpdateFactory.newLatLng(latLng))
            animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        }*/
    }

    private fun getLastLocation() {
        try {
            mFusedLocationClient.getLastLocation()?.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    location = task.getResult()
                    CurrentlatLng = LatLng(location.latitude, location.longitude)
                    Log.e("CurrentLatLong2", "lat " + location.latitude + " long " + location.longitude)
                    currentLatitute = location.latitude
                    currentLongtitute = location.longitude

                    val options = MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin))
                            .position(CurrentlatLng)
                    googleMap?.apply {
                        addMarker(options)
                        moveCamera(CameraUpdateFactory.newLatLng(CurrentlatLng))
                        animateCamera(CameraUpdateFactory.newLatLngZoom(CurrentlatLng, 15f))
                    }

                    //assignToMap()

                } else {
                    Log.w("Location", "Failed to get location.")
                }
            }
        } catch (unlikely: SecurityException) {
            Log.e("Location", "Lost location permission." + unlikely)
        }

    }


    private fun initLocation() {
        try {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this@SearchPlacesActivity)
            getLastLocation()
            try {

                mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                        .addOnSuccessListener(this, object : OnSuccessListener<LocationSettingsResponse> {
                            override fun onSuccess(p0: LocationSettingsResponse?) {
                                mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                        mLocationCallback, Looper.myLooper());
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
                                    rae.startResolutionForResult(this@SearchPlacesActivity, REQUEST_CHECK_SETTINGS);
                                } catch (sie: IntentSender.SendIntentException) {
                                    Log.i("Location", "PendingIntent unable to execute request.");
                                }
                            }

                            LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE ->
                                Toast.makeText(this@SearchPlacesActivity, "Location settings are inadequate, and cannot be \"+\n" +
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


    override fun onMapReady(p0: GoogleMap?) {
        Log.v("googleMap", "googleMap==" + googleMap)
        googleMap = p0
        googleMap?.setMapType(GoogleMap.MAP_TYPE_NORMAL)
        googleMap?.getUiSettings()?.apply {
            isZoomControlsEnabled = false
            isCompassEnabled = true
            isMyLocationButtonEnabled = true
        }

    }


    /* To hide Keyboard */
    fun hideKeyboard() {
        try {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                initLocation()
            } else {
                Toast.makeText(this@SearchPlacesActivity, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

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
            requestPermissions();
        }
    }


}