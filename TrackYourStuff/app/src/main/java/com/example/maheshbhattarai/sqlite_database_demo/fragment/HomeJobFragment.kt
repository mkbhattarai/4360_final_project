package com.example.maheshbhattarai.sqlite_database_demo.fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.maheshbhattarai.sqlite_database_demo.R
import com.example.maheshbhattarai.sqlite_database_demo.database.DBHelper
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.PolylineOptions
import com.example.maheshbhattarai.sqlite_database_demo.Utill.*
import org.json.JSONObject


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HomeJobFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeJobFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeJobFragment : Fragment(), OnMapReadyCallback  , OnParsePath, OnReadPath  {
    override fun onGetPath(s: JSONObject) {

    }

    override fun onGetParsePath(polyLineOptions: PolylineOptions?) {
    }


    override fun onMapReady(googleMap: GoogleMap){
       setMyCurrentLocation(googleMap)
      //To change body of created functions use File | Settings | File Templates.
    }

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var googleMap: GoogleMap? = null

    private var mapView: MapView? = null
    private var mapsSupported = true

    private var mListener: OnFragmentInteractionListener? = null
    lateinit var mContext : Context

    lateinit var  db : DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        try {
            MapsInitializer.initialize(activity!!)
        } catch (e: GooglePlayServicesNotAvailableException) {
            mapsSupported = false
        }
        mapView?.onCreate(savedInstanceState)
        initializeMap()
    }
//    override fun onGetPath(s: JSONObject) {
//        ParserTaskPath(mContext).execute(s.toString())    }


    private fun initializeMap() {
        if (googleMap == null && mapsSupported) {
            mapView = activity!!.findViewById<MapView>(R.id.mapview) as MapView
            mapView!!.getMapAsync(this)
            //setup markers etc...
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView =inflater.inflate(R.layout.fragment_home, container, false)
        mapView = rootView.findViewById<MapView>(R.id.mapview)
        if (container != null) {
            mContext=container.context
        }
        if (mListener!=null){
            onButtonPressed("Home")
        }

        db=DBHelper(mContext)

        if (db!=null){

            var AllJob = db.getAllJob(Apprefence.getJobCat(mContext))
            if (AllJob!=null){
                for (i in AllJob) {
                var job =i
                 job.customer_logitiude
                 job.customer_logitiude
                Log.e("Sidd",""+job)
                }
            }
        }

        return rootView
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: String) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: String)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): HomeJobFragment {
            val fragment = HomeJobFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
        initializeMap()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    fun setMyCurrentLocation(mGoogle : GoogleMap){

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
/*        googleMap.isMyLocationEnabled=true;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
*/
        mGoogle.isMyLocationEnabled=true
        mGoogle.cameraPosition
        mGoogle.isBuildingsEnabled=true
        mGoogle.isTrafficEnabled=true
        mGoogle.isIndoorEnabled=true
        mGoogle.uiSettings.isCompassEnabled=true
        mGoogle.uiSettings.isZoomControlsEnabled=true
        mGoogle.uiSettings.isMapToolbarEnabled=true
        mGoogle.uiSettings.isZoomGesturesEnabled=true

          if (db!=null){
              db= DBHelper(mContext)
                var userInfo = db.getUserInfo(Apprefence.getUserId(mContext),Apprefence.getRole(mContext))
              var JobAll=db.selectedJob(Apprefence.getJobCat(mContext),AppConstant.PENDING)

              Log.e("userD",userInfo.toString())
              Log.e("userD12",JobAll.toString())

              var user_latitude= java.lang.Double.parseDouble(userInfo.user_latitude);
              var user_longititude= java.lang.Double.parseDouble(userInfo.user_longititude);

              if (JobAll!=null) {
                  if (!JobAll.isEmpty()) {
                      for (item in JobAll) {
                          Log.e("Helll", item.toString());
                          var customer_lattitude = java.lang.Double.parseDouble(item.customer_lattitude);
                          var customer_logitiude = java.lang.Double.parseDouble(item.customer_logitiude);

                          createMarker(mGoogle, customer_lattitude, customer_logitiude, "maraker", user_latitude, user_longititude)
                      }

//                  val cameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng(customer_lattitude,customer_logitiude), 10f)
//
////                  val cameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng(tracker.latitude, tracker.longitude), 10f)
//                  mGoogle.animateCamera(cameraUpdate)

                  } else if (userInfo.user_latitude != null && userInfo.user_longititude != null) {
                      mGoogle.isMyLocationEnabled=true
                      mGoogle.isBuildingsEnabled=true
                      mGoogle.isTrafficEnabled=true
                      mGoogle.isIndoorEnabled=true
                      val cameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng(user_latitude, user_longititude), 10f)

//                  val cameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng(tracker.latitude, tracker.longitude), 10f)
                      mGoogle.animateCamera(cameraUpdate)
                  }
              }
          }


//        googleMap.addMarker(marker2)

//        val latLng = LatLng(o_lt, o_lg)
//        val marker2 = MarkerOptions().position(LatLng(o_lt, o_lg)).icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_blue))


        /*protected Marker createMarker(double latitude, double longitude, String title, String snippet, int iconResID) {


}*/
    }




    fun createMarker (mGoogle: GoogleMap,latitude : Double ,longitude :Double,title :String ,userLati : Double
                      ,userLongi :Double) : Marker{
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng(latitude,longitude), 10f)
        mGoogle.moveCamera(cameraUpdate)
//                  val cameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng(tracker.latitude, tracker.longitude), 10f)
        mGoogle.setOnMarkerClickListener(GoogleMap.OnMarkerClickListener { marker ->
            //Using position get Value from arraylist

           var  mTracker = GPSTracker(mContext)
            Log.e("sidd","lati :"+marker.position.latitude + "longi :"+marker.position.longitude)
           var origin= LatLng(marker.position.latitude,marker.position.longitude)
           var dest= LatLng(mTracker.latitude,mTracker.longitude)


            mGoogle.addMarker( MarkerOptions()
                    .position(origin)
                    .anchor(0.5f, 0.5f)
                    .title("marker"))


            val cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(dest, 10f)
            mGoogle.moveCamera(cameraUpdate1)
//            mGoogle.addMarker( MarkerOptions()
//                    .position(dest)
//                    .anchor(0.5f, 0.5f)
//                    .title("you"))

            val urlpath1 = getUrl(origin,dest)

//            Log.e("sidd" + "Destination Latlong", latLng.toString())
            //TODO: Read Google path URL
            ReadTaskPath(mContext,mGoogle).drawPath(urlpath1)


            false
        })
        return mGoogle.addMarker( MarkerOptions()
                .position(LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title))

//                .snippet(snippet)
//                .icon(BitmapDescriptorFactory.fromResource(iconResID)));
    }


    private fun getUrl(origin: LatLng, dest: LatLng): String {

        // Origin of route
        val str_origin = "origin=" + origin.latitude + "," + origin.longitude

        // Destination of route
        val str_dest = "destination=" + dest.latitude + "," + dest.longitude


        // Sensor enabled
        val sensor = "sensor=false"

        // Building the parameters to the web service
        val parameters = "$str_origin&$str_dest&$sensor"

        // Output format
        val output = "json"

        // Building the url to the web service


        return "https://maps.googleapis.com/maps/api/directions/$output?$parameters"
    }


}// Required empty public constructor


