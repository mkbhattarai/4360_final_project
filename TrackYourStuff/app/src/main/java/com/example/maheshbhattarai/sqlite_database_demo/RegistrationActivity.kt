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
import android.widget.Toast.LENGTH_SHORT
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.maheshbhattarai.sqlite_database_demo.Utill.Apprefence
import com.example.maheshbhattarai.sqlite_database_demo.database.AppDatabase
import com.example.maheshbhattarai.sqlite_database_demo.database.DBHelper
import com.example.maheshbhattarai.sqlite_database_demo.database.Registration
import com.example.maheshbhattarai.sqlite_database_demo.model.User
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.google.android.gms.location.places.Place


class RegistrationActivity : AppCompatActivity() {
//
//    override fun onClick(v: View) {
//        when (v.id) {
//            R.id.btn_submit -> {
//                Toast.makeText(this, "Hello, views!", LENGTH_SHORT).show()
//            }
//            }
//    }

    var TAG: String = "RegistrationActivity"
    var PLACE_AUTOCOMPLETE_REQUEST_CODE = 1

    fun onSubmit(view: View) {
        submit()
    }


    lateinit var name: EditText
    lateinit var email: EditText
    lateinit var address: EditText
    lateinit var phoneNumber: EditText
    lateinit var password: EditText
    lateinit var radioRole: RadioGroup
    lateinit var login: TextView
    lateinit var sppiner: Spinner
    //
//    @BindView(R.id.phoneNumber)
//
//    @BindView(R.id.password)
//
//
//    @BindView(R.id.radioRole)
//
//    @BindView(R.id.login)
//
//    @BindView(R.id.radioEmployeer)
    lateinit var radioEmployeer: RadioButton


    lateinit var radioJobSeeker: RadioButton

    var db: DBHelper? = null

    lateinit var mContext: Context
    var jobtype: String? = null

    var user_lattiude: String? = "0.0"
    var user_longititude: String? = "0.0"

    lateinit var handler: Handler;

    fun submit() {
        if (TextUtils.isEmpty(name.text.toString())) {
            name.setError("please enter your name")
        } else if (TextUtils.isEmpty(email.text.toString())) {
            email?.setError("please enter your email")
        } else if (TextUtils.isEmpty(address.text.toString())) {
            address?.setError("please enter your address")
        } else if (TextUtils.isEmpty(phoneNumber.text.toString())) {
            phoneNumber?.setError("please enter your phone no")
        } else if (TextUtils.isEmpty(password.text.toString())) {
            password?.setError("please enter your password")
        } else if (TextUtils.isEmpty(jobtype)) {
            Toast.makeText(mContext, "please select your job type", Toast.LENGTH_LONG).show()
        } else {
//            Toast.makeText(mContext, "please select your job type 1", Toast.LENGTH_LONG).show()
            var dialog = ProgressDialog(mContext)
            dialog.setMessage("please wait..")
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()
            var handler = Handler()
            handler.postDelayed(Runnable {
                var user = User()
                user.name = name.text.toString()
                user.email = email.text.toString()
                user.password = password.text.toString()
                user.jobType = Apprefence.getRole(mContext)
                user.location = ""
                user.phoneno = phoneNumber.text.toString()
                user.login_status = "0"
                user.address = address.text.toString()
                user.user_latitude = user_lattiude
                user.user_longititude = user_longititude
                user.job_cat = sppiner.selectedItem.toString()
                var userinfo = db?.getUserInfo(email.text.toString(),Apprefence.getRole(mContext))

                var isRegistration: Boolean = db?.user(user)!!
                if (userinfo?.email != email.text.toString()) {
                    if (isRegistration) {
                        dialog.dismiss()
                        Toast.makeText(mContext, "Registration Sucessfully", Toast.LENGTH_LONG).show()
                        startActivity(Intent(mContext, HomeActivity::class.java))
                    } else {
                        dialog.dismiss()
                        Toast.makeText(mContext, "Not Registration Sucessfully", Toast.LENGTH_LONG).show()
                    }
                } else {
                    dialog.dismiss()
                    Toast.makeText(mContext, "User Already exits", Toast.LENGTH_LONG).show()

                }

            }, 2000)

        }
    }

    private var userStatus: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        ButterKnife.bind(this)
        mContext = this;
        db = DBHelper(mContext)
        userStatus = intent.getStringExtra("userStatus");
        initComponent()

        //        radioRole?.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener(){
//            group: RadioGroup?, checkedId: Int ->
        radioEmployeer.isChecked=false
        radioJobSeeker.isChecked=false
        radioRole.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            if (checkedId == radioEmployeer?.id) {
                radioEmployeer.isChecked=true
                jobtype = radioEmployeer?.text.toString();
                sppiner.visibility = View.INVISIBLE
            } else if (checkedId == radioJobSeeker?.id) {
                radioJobSeeker.isChecked=true
                jobtype = radioJobSeeker?.text.toString();
                sppiner.visibility = View.VISIBLE
            }
        })

        address.setOnClickListener(View.OnClickListener { onAddressPlace() })
        /* workforNatur = ArrayAdapter<String>(mContext,
                 android.R.layout.simple_spinner_item, mContext.resources.getStringArray(R.array.work_nature))
         workforNatur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
         work_nature.setAdapter(workforNatur)
         workforNatur.notifyDataSetChanged()
 */


        var carAdapter = ArrayAdapter<String>(mContext,
                android.R.layout.simple_spinner_item, mContext.resources.getStringArray(R.array.work_nature))
        carAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sppiner.setAdapter(carAdapter)
        carAdapter.notifyDataSetChanged()

    }


    fun initComponent() {
        radioEmployeer = findViewById<RadioButton>(R.id.radioEmployeer)
        radioJobSeeker = findViewById<RadioButton>(R.id.radioJobSeeker)
        name = findViewById<EditText>(R.id.name)
        email = findViewById<EditText>(R.id.email)
        password = findViewById<EditText>(R.id.password)
        address = findViewById<EditText>(R.id.address)
        phoneNumber = findViewById<EditText>(R.id.phoneNumber)
        radioRole = findViewById<RadioGroup>(R.id.radioRole)
        login = findViewById<TextView>(R.id.login)
        sppiner = findViewById<Spinner>(R.id.sppiner_cat)
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlaceAutocomplete.getPlace(this, data)
                Log.i(TAG, "Place: " + place.latLng.latitude)
                Log.i(TAG, "Place: " + place.latLng.longitude)
                user_lattiude = place.latLng.latitude.toString()
                user_longititude = place.latLng.longitude.toString()
                address.setText(place.address)
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                val status = PlaceAutocomplete.getStatus(this, data)
                // TODO: Handle the error.
                Log.i(TAG, status.statusMessage)

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
//

}
