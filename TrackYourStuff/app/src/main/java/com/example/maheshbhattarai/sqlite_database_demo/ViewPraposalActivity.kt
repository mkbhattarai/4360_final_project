package com.example.maheshbhattarai.sqlite_database_demo

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.maheshbhattarai.sqlite_database_demo.Utill.Apprefence
import com.example.maheshbhattarai.sqlite_database_demo.database.DBHelper

class ViewPraposalActivity : AppCompatActivity() {

    lateinit var job_title : TextView
    lateinit var customer_name : TextView
    lateinit var customer_address : TextView
    lateinit var  work_nature : TextView
    lateinit var estimated_time : TextView
    lateinit var rxt_hourly_v : TextView
    lateinit var mContext :Context

    var db : DBHelper ? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_praposal)

        mContext=this

        initComponent()

       db=DBHelper(mContext)

       var job_title1= intent.getStringExtra("job_title")
       var customer_name1= intent.getStringExtra("customer_name")
       var customer_address1= intent.getStringExtra("customer_address")
       var worknature1= intent.getStringExtra("worknature")
       var estimated_time1= intent.getStringExtra("estimated_time")
       var rxt_hourly_v1= intent.getStringExtra("rxt_hourly_v1")
        job_title.setText(job_title1)
        customer_name.setText(customer_name1)
        customer_address.setText(customer_address1)
        work_nature.setText(worknature1)
        estimated_time.setText(estimated_time1)
        rxt_hourly_v.setText(rxt_hourly_v1)
    }

   fun initComponent(){
       job_title =findViewById<TextView>(R.id.job_title)
       customer_name=findViewById<TextView>(R.id.customer_name)
       customer_address=findViewById<TextView>(R.id.customer_address)
       work_nature=findViewById<TextView>(R.id.work_nature)
       estimated_time=findViewById<TextView>(R.id.estimated_time)
       rxt_hourly_v=findViewById<TextView>(R.id.rxt_hourly_v)
   }

    fun onCanclePraposal(view :View){
        AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getString(R.string.app_name))
                .setMessage("Are you want to delete this job")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()

                    var pDialog= ProgressDialog(mContext)
                    pDialog.setMessage("please wait..")
                    pDialog.setCancelable(false)
                    pDialog.setCanceledOnTouchOutside(false)
                    try {
                        pDialog.show()
                    } catch (e: Exception) {
                    }
                    var handler = Handler()
                    handler.postDelayed(Runnable {
                        pDialog.dismiss()
                       if (db!=null){
                           db?.deleteJob(Apprefence.getJob_id(mContext))
                       }
                        finish()
                    }, 2000)

                })
                .setNegativeButton("No", null)
                .show()


    }

}
