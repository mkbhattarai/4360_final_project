package com.example.maheshbhattarai.sqlite_database_demo

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.example.maheshbhattarai.sqlite_database_demo.Utill.AppConstant
import com.example.maheshbhattarai.sqlite_database_demo.Utill.Apprefence
import com.example.maheshbhattarai.sqlite_database_demo.database.DBHelper
import com.example.maheshbhattarai.sqlite_database_demo.model.JobMdal
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import android.app.DatePickerDialog.OnDateSetListener
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import com.example.maheshbhattarai.sqlite_database_demo.Utill.GPSTracker
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_submission.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


class SubmissionActivity : AppCompatActivity(), View.OnClickListener {
    var TAG = "SubmissionActivity"
    var PLACE_AUTOCOMPLETE_REQUEST_CODE = 1

    var job_title: EditText? = null

    var customer_name: EditText? = null

    var customer_address: EditText? = null

    var estimated_phoneNo: EditText? = null

    var edt_sepenttime: EditText? = null

    var edt_extraChar: EditText? = null

    var edt_dis: EditText? = null

    var work_nature: Spinner? = null

    var estimated_time: EditText? = null

    var txt_hourly_charages: EditText? = null

    var edt_distanceChar: EditText? = null

    var currentLatitude: Double = 0.0

    var currentLogitiude: Double = 0.0

    var db: DBHelper? = null

    private var format = ""
    var btn_submit: Button? = null
    lateinit var workforNatur: ArrayAdapter<String>
    lateinit var estimatedFortime: ArrayAdapter<String>
    lateinit var mContext: Context
    var latititude: String = "0.0"
    var longitiude: String = "0.0"
    var customer_lat: Double = 0.0
    var customer_long: Double = 0.0
    var total_amount: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submission)
        mContext = this
        setToolbar()
        initComponent()
        db = DBHelper(mContext)
        if (db != null) {
            var jModel = db?.singleJob(Apprefence.getJobCat(mContext), Apprefence.getJob_id(mContext))
            Log.e(TAG, jModel.toString())
//            customer_lat = jModel?.customer_lattitude!!.toDouble()
//            currentLogitiude = jModel?.customer_logitiude!!.toDouble()

        }
    }

    fun getCurrentLatLong() {
        var mTracker = GPSTracker(mContext)
        currentLatitude = mTracker.latitude
        currentLogitiude = mTracker.longitude
        convertKilometer(LatLng(currentLatitude, currentLogitiude), LatLng(customer_lat, customer_long))
    }


    fun setToolbar() {

    }

    fun initComponent() {

        job_title = findViewById<EditText>(R.id.job_title)
        customer_name = findViewById<EditText>(R.id.customer_name)
        customer_address = findViewById<EditText>(R.id.customer_address)
        estimated_phoneNo = findViewById<EditText>(R.id.estimated_phoneNo)
        edt_sepenttime = findViewById<EditText>(R.id.edt_sepenttime)
        edt_dis = findViewById<EditText>(R.id.edt_dis)
        edt_extraChar = findViewById<EditText>(R.id.edt_extraChar)
        work_nature = findViewById<Spinner>(R.id.work_nature)
        estimated_time = findViewById<EditText>(R.id.estimated_time)
        btn_submit = findViewById<Button>(R.id.btn_submit)
        txt_hourly_charages = findViewById<EditText>(R.id.txt_hourly_charages)
        edt_distanceChar = findViewById<EditText>(R.id.edt_distanceChar)
        btn_submit?.setOnClickListener(this)

        edt_sepenttime?.setOnClickListener(this)
        estimated_time?.setOnClickListener(this)
        if (txt_hourly_charages?.text.toString() != null) {


        }

//        txt_hourly_charages?.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
//            if(!hasFocus) {
//                getCurrentLatLong()
//                total_amount = getTotalAmmount()
//
//                Log.e(TAG,total_amount)
//
//            }
//
//            Toast.makeText(mContext,"update",Toast.LENGTH_LONG).show()
//
//        })


        workforNatur = ArrayAdapter<String>(mContext,
                android.R.layout.simple_spinner_item, mContext.resources.getStringArray(R.array.work_nature))
        workforNatur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        work_nature?.adapter = workforNatur
        workforNatur.notifyDataSetChanged()

        if (work_nature?.selectedItem.toString() == "plumbing") {
            txt_hourly_charages?.setText("40 $ ")
        }

        work_nature?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (work_nature?.selectedItem.toString() == "plumbing") {
                    txt_hourly_charages?.setText("$ 40  ")
                    getCurrentLatLong()
                } else if (work_nature?.selectedItem.toString() == "painting") {
                    txt_hourly_charages?.setText("$ 45 ")
                    getCurrentLatLong()
                } else if (work_nature?.selectedItem.toString() == "cleaning") {
                    getCurrentLatLong()
                    txt_hourly_charages?.setText(" $ 50 ")
                }
            }
        }
        customer_address?.setOnClickListener(this)


    }


    fun onAddressPlace() {
        try {
            val intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(this)
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE)
        } catch (e: GooglePlayServicesRepairableException) {
            // TODO: Handle the error.
        } catch (e: GooglePlayServicesNotAvailableException) {
            // TODO: Handle the error.
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_submit) {
            if (TextUtils.isEmpty(job_title?.text.toString())) {
                job_title?.setError("please enter your job title")
            } else if (TextUtils.isEmpty(customer_name?.text.toString())) {
                customer_name?.setError("please enter customer name")
            } else if (TextUtils.isEmpty(customer_address?.text.toString())) {
                customer_address?.setError("please select customer address")
            } else if (TextUtils.isEmpty(estimated_phoneNo?.text.toString())) {
                estimated_phoneNo?.setError("enter your phone no")
            } else if (TextUtils.isEmpty(edt_sepenttime?.text.toString())) {
                edt_sepenttime?.setError("enter your spent time ")
            } else if (TextUtils.isEmpty(edt_extraChar?.text.toString())) {
                edt_extraChar?.setError("enter your extra charge")
            } else if (TextUtils.isEmpty(edt_dis?.text.toString())) {
                edt_dis?.setError("enter your short discription")
            } else if (TextUtils.isEmpty(work_nature?.selectedItem.toString())) {
                Toast.makeText(mContext, "please select your work", Toast.LENGTH_LONG).show()
            } else if (TextUtils.isEmpty(estimated_time?.text.toString())) {
                Toast.makeText(mContext, "please select time", Toast.LENGTH_LONG).show()
            } else if (TextUtils.isEmpty(txt_hourly_charages?.text.toString())) {
                Toast.makeText(mContext, "please enter your hourly charges", Toast.LENGTH_LONG).show()
            } else if (TextUtils.isEmpty(edt_distanceChar?.text.toString())) {
                Toast.makeText(mContext, "please enter your distance charges", Toast.LENGTH_LONG).show()
            } else {
                var mDialog = ProgressDialog(mContext)
                mDialog.setMessage("please wait .....")
                mDialog.setCancelable(false)
                mDialog.setCanceledOnTouchOutside(false)
                mDialog.show()
                var handler = Handler()
                handler.postDelayed(Runnable {
                    mDialog.dismiss()
                    var model = JobMdal()
                    model.jobTitle = job_title?.text.toString();
                    model.customerName = customer_name?.text.toString();
                    model.customer_address = customer_address?.text.toString();
                    model.estimated_phoneNo = estimated_phoneNo?.text.toString();
                    model.job_spent_time = edt_sepenttime?.text.toString();
                    model.customer_lattitude = latititude.toString()
                    model.customer_logitiude = longitiude.toString()
                    model.job_extra_charge = edt_extraChar?.text.toString();
                    model.job_short_dis = edt_dis?.text.toString();
                    model.work_nature = work_nature?.selectedItem.toString();
                    model.estimated_time = estimated_time?.text.toString();
                    model.jobApproved = AppConstant.COMPLETED
                    model.hourly_charhge = txt_hourly_charages?.text.toString()
                    model.distance_charge = edt_distanceChar?.text.toString()
                    model.total_amount = getTotalAmmount()
                    var db = DBHelper(mContext)
                    if (db != null) {
                        var isUpdate = db.updateStatus(Apprefence.getJob_id(mContext), model)
                        if (isUpdate) {
                            job_title?.setText("")
                            customer_name?.setText("")
                            customer_address?.setText("")
                            estimated_phoneNo?.setText("")
                            edt_sepenttime?.setText("")
                            edt_extraChar?.setText("")
                            edt_dis?.setText("")
                            estimated_time?.setText("")
                            txt_hourly_charages?.setText("")
                            edt_distanceChar?.setText("")
                            Toast.makeText(mContext, "successfully updated ", Toast.LENGTH_LONG).show()
                            startActivity(Intent(mContext,ReceiptActivity::class.java))

                        } else {
                            Toast.makeText(mContext, "unsuccessfully updated ", Toast.LENGTH_LONG).show()
                        }
                    }


                }, 2000)
            }
        } else if (v.id == R.id.customer_address) {
            onAddressPlace()
        } else if (v.id == R.id.estimated_time) {
            val mcurrentTime = Calendar.getInstance()
            val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
            val minute = mcurrentTime.get(Calendar.MINUTE)
            var mHour = ""
            var mMinitue = ""
//  TimePickerDialog mTimePicker;
            var mTimePicker = TimePickerDialog(mContext, TimePickerDialog.OnTimeSetListener({ view: TimePicker?, hourOfDay: Int, minute: Int ->


                if (hourOfDay < 10) {
                    mHour = "0" + hourOfDay
                } else {
                    mHour = hourOfDay.toString()
                }

                if (minute < 10) {
                    mMinitue = "0" + minute
                } else {
                    mMinitue = minute.toString()

                }

                estimated_time?.setText("\n${mHour}:${mMinitue}")

            }), hour, minute, true)
            mTimePicker.show()
        } else if (v.id == R.id.edt_sepenttime) {
            val mcurrentTime = Calendar.getInstance()
            val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
            val minute = mcurrentTime.get(Calendar.MINUTE)
            var mHour = ""
            var mMinitue = ""
//  TimePickerDialog mTimePicker;
            var mTimePicker = TimePickerDialog(mContext, TimePickerDialog.OnTimeSetListener({ view: TimePicker?, hourOfDay: Int, minute: Int ->


                if (hourOfDay < 10) {
                    mHour = "0" + hourOfDay
                } else {
                    mHour = hourOfDay.toString()
                }

                if (minute < 10) {
                    mMinitue = "0" + minute
                } else {
                    mMinitue = minute.toString()

                }

                edt_sepenttime?.setText("\n${mHour}:${mMinitue}")

            }), hour, minute, true)
            mTimePicker.show()

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
                customer_address?.setText(place.address)

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                val status = PlaceAutocomplete.getStatus(mContext, data)
                // TODO: Handle the error.
                Log.i(TAG, status.statusMessage)

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    fun showTimerPikcerDialog(edtText: EditText) {
        val mcurrentDate = Calendar.getInstance()
        val mYear = mcurrentDate.get(Calendar.YEAR)
        val mMonth = mcurrentDate.get(Calendar.MONTH)
        val mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH)

        val mDatePicker: DatePickerDialog
        mDatePicker = DatePickerDialog(this, OnDateSetListener { datepicker, selectedyear, selectedmonth, selectedday ->
            var selectedmonth = selectedmonth
            // TODO Auto-generated method stub
            /*      Your code   to get date and time    */
            selectedmonth = selectedmonth + 1
            edtText.setText("$selectedday/$selectedmonth/$selectedyear")
        }, mYear, mMonth, mDay)
        mDatePicker.setTitle("Select Date")
        mDatePicker.show()
    }


    fun convertKilometer(origin: LatLng, dest: LatLng) {
        var s = distance(origin.latitude, origin.longitude, dest.latitude, dest.longitude)
        var km = s
        var chargges = txt_hourly_charages?.text.toString().replace("$", "").toDouble()
        var finalCharges = km * chargges
        var totalAM = finalCharges / 60000
        Log.e(TAG, finalCharges.toString());
        edt_distanceChar?.setText(java.lang.String.valueOf(DecimalFormat("##.##").format(totalAM) + " $ "))
    }

    private fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + (Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta)))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist = dist * 60.0 * 1.1515
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }

    fun getTotalAmmount(): String {
        var extra = edt_extraChar?.text.toString().toDouble()
        var hourly = txt_hourly_charages?.text.toString().replace("$", "").trim().toDouble()
        var time = edt_sepenttime?.text.toString().replace(":", ".").toDouble()


        var distanceCha = edt_distanceChar?.text.toString().replace("$", "").toDouble()
        var overAllTotal = extra + hourly * time + distanceCha
        return java.lang.String.valueOf(overAllTotal)
    }
}

