package com.example.maheshbhattarai.sqlite_database_demo

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.example.maheshbhattarai.sqlite_database_demo.Utill.Apprefence
import com.example.maheshbhattarai.sqlite_database_demo.database.AppDatabase
import com.example.maheshbhattarai.sqlite_database_demo.database.DBHelper
import com.example.maheshbhattarai.sqlite_database_demo.model.User
import kotlinx.android.synthetic.main.activity_registration.*


class LoginActivity : AppCompatActivity() {

    var TAG: String = "LoginActivity"

    lateinit var emailId: EditText

    lateinit var password: EditText

    lateinit var mContext: Context

    lateinit var db: DBHelper

    lateinit var useInfo: User
    lateinit var usersStatus: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)
        mContext = this;
        db = DBHelper(mContext)
        initComponent()
        usersStatus = intent.getStringExtra("userStatus");

    }

    fun onLogin(view: View) {
        if (TextUtils.isEmpty(emailId.text.toString()))
            emailId.setError("please enter your email id")
        else if (TextUtils.isEmpty(password.text.toString()))
            password.setError("please enter your password")
        else {
            if (db != null) {
                useInfo = db.getUserInfo(emailId.text.toString(),Apprefence.getRole(mContext))
                if (useInfo!=null) {
                    var dialog = ProgressDialog(mContext)
                    dialog.setMessage("please wait..")
                    dialog.setCancelable(false)
                    dialog.setCanceledOnTouchOutside(false)
                    dialog.show()
                    var handler = Handler()
                    handler.postDelayed(Runnable {
                        Log.e("Sidd", emailId.text.toString())
//                        Log.e("Sidd", useInfo.email)
                        if (useInfo.email == emailId.text.toString()) {
                            dialog.dismiss()
                            Apprefence.setUserId(mContext, useInfo.email.toString())
                            Apprefence.setRole(mContext, useInfo.jobType.toString())
                            Apprefence.setJobcat(mContext, useInfo.job_cat.toString())
                            if (useInfo.jobType == "0") {
                                startActivity(Intent(mContext, DashBroadActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                finish()
                            } else if (useInfo.jobType == "1") {
                                startActivity(Intent(mContext, JobSeekerActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                finish()
                            }
                        } else {
                            dialog.dismiss()
                            Toast.makeText(mContext, "user does not exits.. ", Toast.LENGTH_LONG).show()
                        }
                    }, 2000)
                }else{
                    Toast.makeText(mContext, "user does not exits.. ", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(mContext, "user does not exits.. ", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun initComponent() {
        emailId = findViewById(R.id.emailId)
        password = findViewById(R.id.password)
    }

    fun onCreateAccount(view: View) {
        startActivity(Intent(mContext, RegistrationActivity::class.java).putExtra("userStatus",usersStatus))

    }
//        sharedpreferences = getSharedPreferences("role", Context.MODE_PRIVATE);
//        //val editor = sharedpreferences?.edit()
//
//        sharedpreferencesID = getSharedPreferences("id", Context.MODE_PRIVATE);
//        val editorID = sharedpreferences?.edit()
//
//
//
//        mDb = AppDatabase.getInMemoryDatabase(getApplicationContext());
//
//        val currentDBPath = getDatabasePath("simple.db").absolutePath
//        Log.e("database_path", currentDBPath)
//
//        val emailId = findViewById<EditText>(R.id.emailId)
//        val password = findViewById<EditText>(R.id.password)
//
//
//        val login_type = sharedpreferences?.getString("login_type", "")
//        Log.e("login_type", login_type)
//
//
//        val btn_login = findViewById<Button>(R.id.btn_login)
//
//        btn_login.setOnClickListener(View.OnClickListener {
//            if (login_type?.contains("Employeer")!!) {
//                if (emailId.text.toString().trim().isEmpty()) {
//                    Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
//                } else if (password.text.toString().trim().isEmpty()) {
//                    Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
//                } else {
//
//                    //
//
//                    val employeeList = mDb?.employDao()?.findAllEmploySync()
//                    Log.e("size", employeeList?.size.toString())
//
//                    if (employeeList != null) {
//
//                        for (employee in employeeList) {
//                            Log.e("email", employee.firstName)
//                            if (emailId.text.toString().equals(employee.email) && password.text.toString().equals(employee.password)) {
//                                /* Log.e("email", employee.email)
//                                 Log.e("email", employee.password)*/
//                                Log.e("seekerID",employee.employId.toString())
//                                val intent = Intent(this, RequirmentActivity::class.java)
//                                startActivity(intent)
//
//                            } else {
//                                //Toast.makeText(this, "Email Id and Password not matched", Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                    } else {
//                        Toast.makeText(this, "Email Id and Password not matched", Toast.LENGTH_SHORT).show()
//                    }
//
//                }
//
//            } else {
//
//                if (emailId.text.toString().trim().isEmpty()) {
//                    Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
//                } else if (password.text.toString().trim().isEmpty()) {
//                    Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
//                } else {
//
//                    val employeeList = mDb?.employDao()?.findAllEmploySync()
//                    Log.e("size", employeeList?.size.toString())
//
//                    if (employeeList != null) {
//
//                        for (employee in employeeList) {
//                            Log.e("email", employee.firstName)
//                            if (emailId.text.toString().equals(employee.email) && password.text.toString().equals(employee.password)) {
//                                /* Log.e("email", employee.email)
//                                 Log.e("email", employee.password)*/
//                                Log.e("seekerID",employee.employId.toString())
//                                editorID?.putString("seekerID",employee.employId.toString())
//                                editorID?.commit()
//                                val intent = Intent(this, Job_Listing::class.java)
//                                startActivity(intent)
//
//                            } else {
//                                //Toast.makeText(this, "Email Id and Password not matched", Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                    } else {
//                        Toast.makeText(this, "Email Id and Password not matched", Toast.LENGTH_SHORT).show()
//                    }
//
//                }
//
//
//            }
//
//        })
//
//        val new_account = findViewById<TextView>(R.id.new_account)
//
//        new_account.setOnClickListener(View.OnClickListener {
//            val intent = Intent(this, RegistrationActivity::class.java)
//            startActivity(intent)
//        })
//
//    }
}
