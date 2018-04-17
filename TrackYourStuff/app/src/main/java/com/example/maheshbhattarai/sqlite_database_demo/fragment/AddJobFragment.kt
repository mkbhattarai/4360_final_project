package com.example.maheshbhattarai.sqlite_database_demo.fragment

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.example.maheshbhattarai.sqlite_database_demo.R
import butterknife.ButterKnife
import com.example.maheshbhattarai.sqlite_database_demo.Utill.AppConstant
import com.example.maheshbhattarai.sqlite_database_demo.Utill.Apprefence
import com.example.maheshbhattarai.sqlite_database_demo.Utill.Function.Companion.currentDate
import com.example.maheshbhattarai.sqlite_database_demo.Utill.TimePickerFragment
import com.example.maheshbhattarai.sqlite_database_demo.database.DBHelper
import com.example.maheshbhattarai.sqlite_database_demo.model.JobMdal
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import kotlinx.android.synthetic.main.activity_submission.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AddJobFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AddJobFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddJobFragment : Fragment() {

    var TAG: String = "AddJobFragment"
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null

    private var mParam2: String? = null
    lateinit var job_title: EditText
    lateinit var customer_name: EditText
    lateinit var customer_address: EditText
    lateinit var estimated_phoneNo: EditText
    lateinit var work_nature: Spinner
    lateinit var estimated_time: EditText
    lateinit var workforNatur: ArrayAdapter<String>
    lateinit var estimatedFortime: ArrayAdapter<String>
    lateinit var mContext: Context
    var PLACE_AUTOCOMPLETE_REQUEST_CODE = 1
    lateinit var db: DBHelper
    private var mListener: OnFragmentInteractionListener? = null
    var latititude: String = "0.0"
    var longitiude: String = "0.0"
    private lateinit var btn_submit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_add_job, container, false)

        activity?.let { ButterKnife.bind(it, rootView) }

        if (container != null) {
            mContext = container.context
        }
        if (mListener!=null){
            onButtonPressed("Add Job")
        }
        initComponent(rootView)

        db = DBHelper(mContext)

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
         * @return A new instance of fragment AddJobFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): AddJobFragment {
            val fragment = AddJobFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }


    /*lateinit var  customer_name :String
        lateinit var  customer_address :String
        lateinit var  estimated_phoneNo :String
        lateinit var  work_nature :String
        lateinit var estimated_time :String
    */


    fun initComponent(rootView: View) {
        job_title = rootView.findViewById<EditText>(R.id.job_title)
        customer_name = rootView.findViewById<EditText>(R.id.customer_name)
        customer_address = rootView.findViewById<EditText>(R.id.customer_address)
        estimated_phoneNo = rootView.findViewById<EditText>(R.id.estimated_phoneNo)
        work_nature = rootView.findViewById<Spinner>(R.id.work_nature)
        estimated_time = rootView.findViewById<EditText>(R.id.estimated_time)
//        hourly_charge = rootView.findViewById<EditText>(R.id.hourly_charge)
        btn_submit=rootView.findViewById<Button>(R.id.btn_submit)


        workforNatur = ArrayAdapter<String>(mContext,
                android.R.layout.simple_spinner_item, mContext.resources.getStringArray(R.array.work_nature))
        workforNatur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        work_nature.adapter = workforNatur
        workforNatur.notifyDataSetChanged()
//        estimatedFortime = ArrayAdapter<String>(mContext,
//                android.R.layout.simple_spinner_item, mContext.resources.getStringArray(R.array.estimateTime))
//        estimatedFortime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        estimated_time.setAdapter(estimatedFortime)
//        estimatedFortime.notifyDataSetChanged()

//        if (work_nature.selectedItem.toString()=="plumbing"){
//            hourly_charge.setText("40 $ ")
//        }

//        work_nature.onItemSelectedListener =object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                if (work_nature.selectedItem.toString()=="plumbing"){
//                    hourly_charge.setText("40 $ ")
//                }else if (work_nature.selectedItem.toString()=="painting"){
//                    hourly_charge.setText("45 $")
//                }else if (work_nature.selectedItem.toString()=="cleaning"){
//                    hourly_charge.setText("50 $")
//                }
//            }
//        }

                 estimated_time.setOnClickListener(View.OnClickListener { v: View? ->
             val mcurrentTime = Calendar.getInstance()
             val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
             val minute = mcurrentTime.get(Calendar.MINUTE)
             var mHour = ""
             var mMinitue=""
//  TimePickerDialog mTimePicker;
         var   mTimePicker = TimePickerDialog(mContext, TimePickerDialog.OnTimeSetListener({
             view: TimePicker?, hourOfDay: Int, minute: Int ->


             if (hourOfDay < 10 ){
               mHour="0"+hourOfDay
             }else{
                 mHour= hourOfDay.toString()
             }

             if(minute < 10){
                 mMinitue = "0"+minute
             } else {
                 mMinitue = minute.toString()

             }

             estimated_time.setText("\n${mHour}:${mMinitue}")

         }),hour,minute,true)
      mTimePicker.show()

//
//              fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
//                 // Do something with the returned time
////
////
////                 var mHourString =""
////                 var mMinString = ""
////
////                 if (hourOfDay < 10 ){
////                     mHourString="0"
////
////                 }else{
////                     mHourString= "\n${getHourAMPM(hourOfDay)}"
////                 }
////
////                 if(minute < 10){
////                     mMinString = "0"+"$minute ${getAMPM(hourOfDay)}"
////                 } else {
////                     mMinString ="$minute ${getAMPM(hourOfDay)}"
////
////                 }
////        var mHourStringIn =java.lang.Integer.parseInt(mHourString)
////        var mMinStringIn = java.lang.Integer.parseInt(mMinString)
//
////                 Log.e("Sidd","Hours : "+mHourString + "Miniustes :" +mMinString)
////
////
////                 var s =
////                         edt.setText(mHourString+"\n${getHourAMPM(hourOfDay)}+":"+mMinString)
//
//             }
//             }, hour, minute, true);
         })

        customer_address.setOnClickListener(View.OnClickListener { v: View? ->
            onAddressPlace()
        })

        btn_submit.setOnClickListener(View.OnClickListener { v: View? ->
         AddNewJob()

        })


    }

    fun onAddressPlace() {
        try {
            val intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(activity)
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE)
        } catch (e: GooglePlayServicesRepairableException) {
            // TODO: Handle the error.
        } catch (e: GooglePlayServicesNotAvailableException) {
            // TODO: Handle the error.
        }
    }


 private fun AddNewJob(){
     if (TextUtils.isEmpty(job_title.text.toString())) {
         job_title.error = "please enter your job title"
     } else if (TextUtils.isEmpty(customer_name.text.toString())) {
         customer_name.error = "please enter customer name"
     } else if (TextUtils.isEmpty(customer_address.text.toString())) {
         customer_address.error = "please select customer address"
     } else if (TextUtils.isEmpty(estimated_phoneNo.text.toString())) {
         estimated_phoneNo.error = "enter your phone no"
     } else if (TextUtils.isEmpty(work_nature.selectedItem.toString())) {
         Toast.makeText(mContext, "please select your work", Toast.LENGTH_LONG).show()
     } else if (TextUtils.isEmpty(estimated_time.text.toString())) {
         Toast.makeText(mContext, "please select time", Toast.LENGTH_LONG).show()
     } else {

         var pDialog = ProgressDialog(mContext)
         pDialog.setMessage("Creating Job....!")
         pDialog.setCanceledOnTouchOutside(false)
         pDialog.setCancelable(false)
         pDialog.show()
         var handler = Handler()

         handler.postDelayed(Runnable {
             pDialog.dismiss()
             var jModal = JobMdal()
             jModal.jobTitle = job_title.text.toString()
             jModal.customerName = customer_name.text.toString()
             jModal.customer_address = customer_address.text.toString()
             jModal.estimated_phoneNo = estimated_phoneNo.text.toString()
             Log.e("Sidd",estimated_time.text.toString())
             jModal.estimated_time = estimated_time.text.toString()
             jModal.work_nature = work_nature.selectedItem.toString()
             jModal.customer_lattitude = latititude
             jModal.customer_logitiude = longitiude
             jModal.jobDateAndTime = currentDate()
             jModal.jobApproved=AppConstant.PENDING
             jModal.job_created_by= Apprefence.getUserId(mContext)
             if (db != null) {
                 Log.e("sidd",jModal.toString())
                 var isPost = db.postJob(jModal)
                 if (isPost) {
                     customer_name.setText("")
                     customer_address.setText("")
                     job_title.setText("")
                     estimated_phoneNo.setText("")
                     estimated_time.setText("")
                     Toast.makeText(mContext, "Successfully Posted your Job..!", Toast.LENGTH_LONG).show()
                 } else {
                     Toast.makeText(mContext, "Un Successfully Posted your Job..!", Toast.LENGTH_LONG).show()
                 }
             }
         }, 2000)
     }
     }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlaceAutocomplete.getPlace(mContext, data)
                Log.i(TAG, "Place: " + place.latLng.latitude)
                Log.i(TAG, "Place: " + place.latLng.longitude)
                latititude = place.latLng.latitude.toString()
                longitiude = place.latLng.longitude.toString()
                customer_address.setText(place.address)

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                val status = PlaceAutocomplete.getStatus(mContext, data)
                // TODO: Handle the error.
                Log.i(TAG, status.statusMessage)

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
//    fun showTimerPikcerDialog(edtText: EditText) {
//        val mcurrentDate = Calendar.getInstance()
//        val mYear = mcurrentDate.get(Calendar.YEAR)
//        val mMonth = mcurrentDate.get(Calendar.MONTH)
//        val mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH)
//        var  c = Calendar.getInstance();
//        var mHour = c.get(Calendar.HOUR_OF_DAY);
//        var mMinute = c.get(Calendar.MINUTE);


//       { timePicker, hour, minute ->
//            textView.text = "Hour: "+ hour + " Minute : "+ minute
//        })
}// Required empty public constructor
////////