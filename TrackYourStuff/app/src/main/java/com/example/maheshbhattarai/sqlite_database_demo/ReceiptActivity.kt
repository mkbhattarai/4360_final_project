package com.example.maheshbhattarai.sqlite_database_demo

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.maheshbhattarai.sqlite_database_demo.Utill.AppConstant
import com.example.maheshbhattarai.sqlite_database_demo.Utill.Apprefence
import com.example.maheshbhattarai.sqlite_database_demo.database.DBHelper
import com.example.maheshbhattarai.sqlite_database_demo.model.JobMdal
import android.content.ActivityNotFoundException
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.text.Html





class ReceiptActivity : AppCompatActivity() {

    var txt_job_title1V : TextView ?=null
    var txt_job_customer_name : TextView ? =null
    var txt_job_customer_address : TextView ? =null
    var txt_job_customer_phonn : TextView ? =null
    var txt_short_dis : TextView ? =null
    var txt_job_cat : TextView ? =null
    var txt_spent_time : TextView ? =null
    var txt_job_hourly_charges : TextView ? =null
    var txt_job_extra_charges : TextView ? =null
    var txt_job_distane_charges : TextView ? =null
    var txt_job_total_ammount : TextView ? =null
    var txt_subTotal : TextView ? =null
    var btn_send : Button ? =null
    lateinit  var mContext : Context

    var db : DBHelper ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)
        mContext=this
/* .putExtra("job_title",user.jobTitle)
                            .putExtra("customer_name",user.customerName)
                            .putExtra("customer_address",user.customer_address)
                            .putExtra("estimated_phoneNo",user.estimated_phoneNo)
                            .putExtra("job_short_dis",user.job_short_dis)
                            .putExtra("work_nature",user.work_nature)
                            .putExtra("job_spent_time",user.job_spent_time)
                            .putExtra("hourly_charhge",user.hourly_charhge)
                            .putExtra("job_extra_charge",user.job_extra_charge)
                            .putExtra("distance_charge",user.distance_charge)
                            .putExtra("total_amount",user.total_amount))*/
        db = DBHelper(mContext)

        setToolbar()
        initComponent()

        Log.e("Sidd",Apprefence.getJobCat(mContext))
        if (db!=null){
            var model=db?.singleJob(AppConstant.COMPLETED,Apprefence.getJob_id(mContext))
            Log.e("COMPLETED",model?.jobTitle)

            txt_job_title1V?.setText(model?.jobTitle)
            txt_job_customer_name?.setText(model?.customerName)
            txt_job_customer_address?.setText(model?.customer_address)
            txt_job_customer_phonn?.setText(model?.estimated_phoneNo)
            txt_short_dis?.setText(model?.job_short_dis)
            txt_job_cat?.setText(model?.work_nature)
            txt_spent_time?.setText(model?.job_spent_time)
            txt_job_hourly_charges?.setText(model?.hourly_charhge)
            txt_job_extra_charges?.setText(model?.job_extra_charge)
            txt_job_distane_charges?.setText(model?.distance_charge)
            txt_job_total_ammount?.setText(model?.total_amount)

            var hourly =model?.hourly_charhge.toString().replace("$", "").trim().toDouble()
            var time = model?.job_spent_time.toString().replace(":", ".").toDouble()
            var overAllTotal =hourly * time
            Log.e("Amount",overAllTotal.toString())
            txt_subTotal?.setText(overAllTotal.toString())
            btn_send?.setOnClickListener(View.OnClickListener { v: View? ->

//               var intent =  Intent(Intent.ACTION_SEND);
//
//                intent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
//                intent.putExtra(Intent.EXTRA_EMAIL,"");
//                intent.putExtra(Intent.EXTRA_SUBJECT, "Work Receipt");
//                intent.putExtra(Intent.EXTRA_HTML_TEXT, Html.fromHtml(StringBuilder()
//                        .append("<p><b>Some Content</b></p>")
//                        .append("<small><p>More content</p></small>")
//                        .toString()));
//
////                intent.setType("message/rfc822");
//
//                try {
//                    startActivity(intent)
//                } catch (ex: ActivityNotFoundException) {
//                    // handle error
//                }
                val mailto = "mailto:bob@example.org" +
                        "?cc=" + "" +
                        "&subject=" + Uri.encode("Work Receipt") +
                        "&body=" + Uri.encode("Job Title :"+model?.jobTitle+"\n\n"+
                        "Customer Name :"+model?.customerName+"\n" +
                        "\n"+"Customer Address :"+model?.customer_address
                        +"\n" +
                        "\n"+"Customer Phone No. :"+model?.estimated_phoneNo+"\n" +
                        "\n"+"Short Description :"+model?.job_short_dis
                  +"\n\n"+"Job Category :"+model?.work_nature
                        +"\n\n"+"Spent Time :"+model?.job_spent_time+"\n\n"
                        +"Hourly Charges :"+model?.hourly_charhge+"\n\n"+
                "Sub Total :"+overAllTotal +"\n\n"+"Extra Charges :"+model?.job_extra_charge
                        +"\n\n"+"Distance Charges :"+model?.distance_charge
                        +"\n\n"+"Total Amount :"+model?.total_amount)

                val emailIntent = Intent(Intent.ACTION_SENDTO)
                emailIntent.data = Uri.parse(mailto)

                try {
                    startActivity(emailIntent)
                    finish()
                } catch (e: ActivityNotFoundException) {
                    //TODO: Handle case where no email app is available
                }

            })


        }
    }



    fun setToolbar(){

    }

    fun initComponent(){
        txt_job_title1V=findViewById<TextView>(R.id.txt_job_title1V)
        txt_job_customer_name=findViewById<TextView>(R.id.txt_job_customer_name)
        txt_job_customer_address=findViewById<TextView>(R.id.txt_job_customer_address)
        txt_job_customer_phonn=findViewById<TextView>(R.id.txt_job_customer_phonn)
        txt_short_dis=findViewById<TextView>(R.id.txt_short_dis)
        txt_job_cat=findViewById<TextView>(R.id.txt_job_cat)
        txt_spent_time=findViewById<TextView>(R.id.txt_spent_time)
        txt_job_hourly_charges=findViewById<TextView>(R.id.txt_job_hourly_charges)
        txt_job_extra_charges=findViewById<TextView>(R.id.txt_job_extra_charges)
        txt_job_distane_charges=findViewById<TextView>(R.id.txt_job_distane_charges)
        txt_job_total_ammount=findViewById<TextView>(R.id.txt_job_total_ammount)
        txt_subTotal=findViewById<TextView>(R.id.txt_subTotal)
        btn_send=findViewById<Button>(R.id.btn_send)
    }

   private fun setJob(model: JobMdal?){
       Log.e("Recipet",model.toString())

    }

}
