package com.example.maheshbhattarai.sqlite_database_demo

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.content.SharedPreferences
import android.R.id.edit
import android.annotation.SuppressLint
import android.util.Log
import com.example.maheshbhattarai.sqlite_database_demo.Utill.Apprefence
import com.example.maheshbhattarai.sqlite_database_demo.database.DBHelper
import com.example.maheshbhattarai.sqlite_database_demo.model.User


class HomeActivity : AppCompatActivity() {
    var TAG :String  ="HomeActivity"
    lateinit var btn_company: Button
    lateinit var btn_jobSeeker: Button
    var sharedpreferences: SharedPreferences? = null

     var db: DBHelper ? =null
    lateinit var mCotnext: Context
     lateinit var userInfo :User
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mCotnext = this;
//        db = DBHelper(mCotnext)
//         if (db?.getUserInfo() != null){
//            userInfo= db?.getUserInfo()!!
//            Log.e(TAG ,userInfo.toString())
//        }

    }

    fun onEmployer(view: View){
        Apprefence.setRole(mCotnext,"0")
        startActivity( Intent(this, LoginActivity::class.java).putExtra("userStatus","0"))
    }
    fun onJobSeeker(view: View){
        Apprefence.setRole(mCotnext,"1")
        startActivity( Intent(this, LoginActivity::class.java).putExtra("userStatus","1"))

    }


}
